package com.nettm.exercise.asynchronous;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class FutureService {

    private final ExecutorService executorService;

    public FutureService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void process() {
        List<Long> aList = LongStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

        List<Future> futures = new ArrayList<>(aList.size());
        aList.forEach(s -> {
            Future future = executorService.submit(() -> System.out.println(s));
            futures.add(future);
        });

        futures.forEach(s -> {
            try {
                s.get(10, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        });
    }
}
