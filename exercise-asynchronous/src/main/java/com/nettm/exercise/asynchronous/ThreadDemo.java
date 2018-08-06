package com.nettm.exercise.asynchronous;

public class ThreadDemo {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                while (Thread.currentThread().isAlive()) {
                    break;
                }
            }

        });

        t.start();
    }

}
