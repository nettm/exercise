package com.nettm.exercise.asynchronous;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelayQueue {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<MyDelayed> queue = new DelayQueue<>();

        MyDelayed delayed1 = new MyDelayed("aa", 5, TimeUnit.SECONDS);
        MyDelayed delayed2 = new MyDelayed("bb", 10, TimeUnit.SECONDS);
        MyDelayed delayed3 = new MyDelayed("cc", 15, TimeUnit.SECONDS);

        queue.put(delayed2);
        queue.put(delayed1);
        queue.put(delayed3);

        System.out.println("订单延迟队列开始时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        while (!queue.isEmpty()) {
            MyDelayed delayed = queue.poll();
            if (delayed != null) {
                System.out.format("订单:{%s}被取消, 取消时间:{%s}\n", delayed.name, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }

            Thread.sleep(1000);
        }
    }

    static class MyDelayed implements Delayed {

        @Getter
        private long time;

        @Getter
        private String name;

        public MyDelayed(String name, long time, TimeUnit unit) {
            this.name = name;
            this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return this.time - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            long diff = this.time - ((MyDelayed) o).getTime();
            if (diff == 0) {
                return 0;
            } else if (diff < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}


