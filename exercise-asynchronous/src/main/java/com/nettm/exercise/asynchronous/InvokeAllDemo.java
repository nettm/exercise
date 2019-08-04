package com.nettm.exercise.asynchronous;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAllDemo<T extends Job> {

    private final List<T> jobList;

    private ExecutorService executorService;

    public InvokeAllDemo(List<T> jobList) {
        this.jobList = jobList;
        this.executorService = createExecutor();
    }

    public List<Object> execute() {
        List<Task> taskList = new ArrayList<>(jobList.size());
        List<Object> objects = new ArrayList<>(jobList.size());
        jobList.forEach(s -> {
            taskList.add(new Task(s));
        });

        try {
            List<Future<Object>> futures = executorService.invokeAll(taskList);
            futures.forEach(s -> {
                try {
                    objects.add(s.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public void shutdown() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private ExecutorService createExecutor() {
        int threadCount = Runtime.getRuntime().availableProcessors();
        ThreadFactoryBuilder factoryBuilder = new ThreadFactoryBuilder();
        factoryBuilder.setNameFormat("test-%d");

        return new ThreadPoolExecutor(threadCount, threadCount, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1000), factoryBuilder.build());
    }

    public static void main(String[] args) {
        List<MyJob> myJobs = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            MyJob job = new MyJob(i);
            myJobs.add(job);
        }

        System.out.println("任务开始");
        InvokeAllDemo mutiProcess = new InvokeAllDemo(myJobs);
        List<Object> resultList = mutiProcess.execute();
        mutiProcess.shutdown();
        System.out.println("任务结束");

        resultList.forEach(s -> System.out.println("结果：" + s));
    }

    class Task implements Callable<Object> {

        private Job job;

        public Task(Job job) {
            this.job = job;
        }

        @Override
        public Object call() throws Exception {
            return job.process();
        }

    }

}

interface Job<R> {

    R process();
}

class MyJob implements Job {

    private int jobId;

    public MyJob(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public Object process() {
        System.out.println("开始:" + Thread.currentThread().getName() + ":" + jobId);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束:" + Thread.currentThread().getName() + ":" + jobId);
        return jobId;
    }
}
