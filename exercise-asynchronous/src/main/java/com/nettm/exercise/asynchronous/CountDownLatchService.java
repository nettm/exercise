package com.nettm.exercise.asynchronous;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDownLatchService {

    private final CountDownLatch lock;
    private final ExecutorService executorService;
    private List<Long> aList;

    public CountDownLatchService(ExecutorService executorService, List<Long> aList) {
        this.executorService = executorService;
        this.lock = new CountDownLatch(aList.size());
        this.aList = aList;
    }

    public void process() throws InterruptedException {
        aList.forEach(s -> {
            executorService.submit(new Task(s));
        });

        lock.await(10, TimeUnit.SECONDS);
    }

    class Task implements Runnable {

        private Long job;

        public Task(Long job) {
            this.job = job;
        }

        @Override
        public void run() {
            try {
                System.out.println(job);
            } finally {
                lock.countDown();
            }
        }
    }

}
