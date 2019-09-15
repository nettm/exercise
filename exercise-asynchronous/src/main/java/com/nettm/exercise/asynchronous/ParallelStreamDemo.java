package com.nettm.exercise.asynchronous;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ParallelStreamDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        test1();
    }

    private static void test() throws ExecutionException, InterruptedException {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

        ForkJoinPool customThreadPool = ForkJoinPool.commonPool();
        long actualTotal = customThreadPool.submit(
            () -> aList.parallelStream().reduce(0L, Long::sum)).get();
        System.out.println(actualTotal);

        ForkJoinTask task = customThreadPool.submit(() -> aList.parallelStream().forEach(s -> {
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }));
        task.get();
        customThreadPool.shutdown();
    }

    private static void test1() {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

        int threadCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool customThreadPool = new ForkJoinPool(threadCount);
        parallelExecute(customThreadPool, () -> aList.parallelStream().forEach(s -> {
            if (s % 100 == 0) {
                throw new  NullPointerException();
            }
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }));
        customThreadPool.shutdown();

    }

    public static void test2() {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("rpc-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0,
            TimeUnit.SECONDS, new SynchronousQueue<>(), factory);
        executorService.execute(() -> aList.parallelStream().forEach(s -> {
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }));
        //aList.parallelStream().forEach(s -> {
        //    System.out.println(Thread.currentThread().getName() + ":" + s);
        //});
        executorService.shutdown();
    }

    public static void test3() {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

        List<CompletableFuture<String>> futureList = new ArrayList<>(aList.size());
        aList.forEach(s -> {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> String.valueOf(s));
            futureList.add(future);
        });

        String combined = futureList.stream().map(CompletableFuture::join).collect(Collectors.joining(" "));
        System.out.println(combined);
    }

    public static void test4() throws InterruptedException, ExecutionException, TimeoutException {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());
        execute1(aList, s -> System.out.println(Thread.currentThread().getName() + ":" + s));
    }

    public static void test5() throws InterruptedException, ExecutionException, TimeoutException {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());
        List<String> result = execute2(aList, String::valueOf);
        System.out.println(result);
    }

    private static <T> void execute1(List<T> list, Consumer<T> func)
        throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture[] futureList = list.stream().map(s -> CompletableFuture.runAsync(() -> func.accept(s), executorService)).
            toArray(CompletableFuture[]::new);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureList);
        combinedFuture.get(10, TimeUnit.SECONDS);

        executorService.shutdown();
    }

    private static <T, R> List<R> execute2(List<T> list, Function<T, R> func)
        throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<R>[] futureList = list.stream().map(s -> CompletableFuture.supplyAsync(() -> func.apply(s), executorService)).
            toArray(CompletableFuture[]::new);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureList);
        combinedFuture.get(10, TimeUnit.SECONDS);

        List<R> result = new ArrayList<>(list.size());
        for (CompletableFuture future : futureList) {
            result.add((R) future.get());
        }

        executorService.shutdown();
        return result;
    }

    private static void parallelExecute(ForkJoinPool pool, Runnable task) {
        Future future = pool.submit(task);
        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

    }

}
