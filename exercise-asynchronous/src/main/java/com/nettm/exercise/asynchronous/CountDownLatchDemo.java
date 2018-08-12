package com.nettm.exercise.asynchronous;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

	public static void main(String[] args) {
		CountDownLatch lock = new CountDownLatch(2);
		Thread t1 = new Thread(new Demo(false, lock));
		Thread t2 = new Thread(new Demo(false, lock));
		t1.start();
		t2.start();
	}

}

class Demo implements Runnable {

	private final boolean isSleep;

	private final CountDownLatch lock;

	public Demo(boolean isSleep, CountDownLatch lock) {
		this.isSleep = isSleep;
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			lock.countDown();
			System.out.println(Thread.currentThread().getName());
			if (isSleep) {
				Thread.currentThread().sleep(1000L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
