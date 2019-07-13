package com.nettm.exercise.asynchronous.runnable;

import com.google.common.collect.Lists;

import java.util.List;

public class MutiProcessDemo {

    public static void main(String[] args) {
        List<MyJob> myJobs = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            MyJob job = new MyJob(i);
            myJobs.add(job);
        }

        System.out.println("任务开始");
        MutiProcess mutiProcess = new MutiProcess(myJobs);
        mutiProcess.execute();
        mutiProcess.shutdown();
        System.out.println("任务结束");
    }
}

class MyJob implements Job {

    private int jobId;

    public MyJob(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public void process() {
        System.out.println("开始:" + Thread.currentThread().getName() + ":" + jobId);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束:" + Thread.currentThread().getName() + ":" + jobId);
    }
}