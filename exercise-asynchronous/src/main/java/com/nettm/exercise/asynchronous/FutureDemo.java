package com.nettm.exercise.asynchronous;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "ok";
            }

        });

        Thread t = new Thread(task);
        t.start();

        String result = task.get();
        System.out.println(result);
    }

}
