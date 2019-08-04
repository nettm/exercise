package com.nettm.exercise.asynchronous;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFutureDemo demo = new CompletableFutureDemo();
        Future<String> future = demo.calculateAsync();
        System.out.println(future.get());

        demo.demo1();

        demo.demo2();

        demo.demo3();

        demo.demo4();

        demo.demo5();

    }

    private Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    private void demo1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");
        System.out.println(future.get());
    }

    private void demo2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<Void> future = completableFuture.thenAccept(
            s -> System.out.println("Computation returned: " + s));
        System.out.println(future.get());
    }

    private void demo3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<Void> future = completableFuture.thenRun(() -> System.out.println("Computation finished."));
        System.out.println(future.get());
    }

    private void demo4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello").thenCompose(
            s -> CompletableFuture.supplyAsync(() -> s + " World"));
        System.out.println(completableFuture.get());
    }

    private void demo5() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
        System.out.println(combinedFuture.get());

        System.out.println(future1.isDone());
        System.out.println(future2.isDone());
        System.out.println(future3.isDone());

        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());

        String combined = Stream.of(future1, future2, future3)
            .map(CompletableFuture::join)
            .collect(Collectors.joining(" "));
        System.out.println(combined);
    }

    private <I, O> List<O> demo6(List<I> list) throws InterruptedException, ExecutionException, TimeoutException {
        Executor executor = Executors.newFixedThreadPool(5);

        CompletableFuture<O>[] futures = list.stream()
            .map(p -> CompletableFuture.supplyAsync(() -> "Hello", executor))
            .toArray(CompletableFuture[]::new);
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(futures);
        completableFuture.get(100, TimeUnit.SECONDS);

        List<O> result = new ArrayList<>();
        for (CompletableFuture<O> future : futures) {
            result.add(future.get());
        }

        return result;
    }
}
