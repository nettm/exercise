package com.nettm.exercise.asynchronous.runnable;

import java.util.List;
import java.util.concurrent.*;

public class MutiProcess<T extends Job> {

    private final List<T> jobList;

    private final CountDownLatch lock;

    private ExecutorService executorService;

    public MutiProcess(List<T> jobList) {
        this.jobList = jobList;
        lock = new CountDownLatch(jobList.size());
        executorService = createExecutor();
    }

    public MutiProcess(List<T> jobList, ExecutorService executorService) {
        this.jobList = jobList;
        lock = new CountDownLatch(jobList.size());
        this.executorService = executorService;
    }

    private ExecutorService createExecutor() {
        int threadCount = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(threadCount, threadCount, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1000), new WorkerThreadFactory("test-"));
    }

    public void execute() {
        jobList.forEach(s -> {
            executorService.submit(new Task(s));
        });
        try {
            lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    class Task implements Runnable {

        private Job job;

        public Task(Job job) {
            this.job = job;
        }

        @Override
        public void run() {
            try {
                job.process();
            } finally {
                lock.countDown();
            }
        }
    }

}

class WorkerThreadFactory implements ThreadFactory {

    private int counter = 0;

    private String prefix;

    public WorkerThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-" + counter++);
    }
}

