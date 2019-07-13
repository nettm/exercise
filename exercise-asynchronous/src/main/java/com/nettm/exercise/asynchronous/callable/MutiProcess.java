package com.nettm.exercise.asynchronous.callable;

import com.google.common.collect.Lists;

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
            new LinkedBlockingQueue<>(1000), new WorkerThreadFactory("test"));
    }

    public List<Object> execute() {
        List<FutureTask> taskList = Lists.newArrayList();
        List<Object> resultList = Lists.newArrayList();
        jobList.forEach(s -> {
            FutureTask<Object> task = new FutureTask<>(new Task(s));
            executorService.submit(task);
            taskList.add(task);
        });

        try {
            lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        taskList.forEach(s -> {
            try {
                Object o = s.get();
                resultList.add(o);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        return resultList;
    }

    public void shutdown() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    class Task implements Callable<Object> {

        private Job job;

        public Task(Job job) {
            this.job = job;
        }

        @Override
        public Object call() throws Exception {
            Object obj;
            try {
                obj = job.process();
            } finally {
                lock.countDown();
            }
            return obj;
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

