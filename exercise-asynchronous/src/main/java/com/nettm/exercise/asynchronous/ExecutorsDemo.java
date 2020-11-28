package com.nettm.exercise.asynchronous;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsDemo {

    private static final ExecutorService pool = new ThreadPoolExecutor(0, 3,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory("KAFKA-CONSUMER-MANAGER"));
    private volatile boolean running = false;

    public static void main(String[] args) {
        pool.submit(new Task(1));
        pool.submit(new Task(2));
        pool.submit(new Task(3));
        pool.submit(new Task(4));
        pool.submit(new Task(5));
    }

    private static class Task implements Runnable {

        private final int num;

        public Task(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    TimeUnit.MILLISECONDS.sleep(300L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public DefaultThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = name + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    public void shutdown() {
        pool.shutdown();
        running = false;
    }

}
