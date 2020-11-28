package com.nettm.exercise.asynchronous;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ParallelService {

    /**
     * 并行执行，无返回结果
     *
     * @param executorService
     * @param list
     * @param func
     * @param timeout
     * @param <T>
     */
    public <T> void parallelExecute(ExecutorService executorService, List<T> list, Consumer<T> func, long timeout) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        CompletableFuture[] futureList = list.stream().map(s -> CompletableFuture.runAsync(() -> func.accept(s),
                executorService)).toArray(CompletableFuture[]::new);
        wait(futureList, timeout);
    }

    /**
     * 并行执行，有返回结果
     *
     * @param executorService
     * @param list
     * @param func
     * @param timeout
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R> List<R> parallelGet(ExecutorService executorService, List<T> list, Function<T, R> func, long timeout) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        CompletableFuture[] futureList = list.stream().map(s -> CompletableFuture.supplyAsync(() -> {
            return func.apply(s);
        }, executorService)).toArray(CompletableFuture[]::new);
        wait(futureList, timeout);

        List<R> result = new ArrayList<>(list.size());
        for (CompletableFuture future : futureList) {
            result.add((R) future.getNow(null));
        }

        return result;
    }

    private void wait(CompletableFuture[] futureList, long timeout) {
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureList);
        try {
            combinedFuture.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}