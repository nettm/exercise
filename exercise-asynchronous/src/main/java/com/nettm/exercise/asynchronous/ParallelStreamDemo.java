package com.nettm.exercise.asynchronous;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ParallelStreamDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }

    private static void test() throws ExecutionException, InterruptedException {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

        int threadCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool customThreadPool = new ForkJoinPool(threadCount);
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
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }));
        customThreadPool.shutdown();

    }

    private static void parallelExecute(ForkJoinPool pool, Runnable task) {
        Future future = pool.submit(task);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
