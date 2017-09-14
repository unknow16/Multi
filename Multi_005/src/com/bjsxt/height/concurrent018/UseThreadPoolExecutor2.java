package com.bjsxt.height.concurrent018;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UseThreadPoolExecutor2 implements Runnable{

	private static AtomicInteger count = new AtomicInteger(0);
	
	@Override
	public void run() {
		try {
			int temp = count.incrementAndGet();
			System.out.println("任务" + temp);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用无界队列时，当有新任务到来时，系统的线程数小于corePoolSize时，则新建线程执行任务，
	 * 达到corePoolSize后，就不会继续增加，
	 * 若后续仍有新的任务加入，而有没有空闲的线程资源，则任务直接进入队列等待，
	 * 若任务创建和处理的速度差异很大，无界队列会保持快速增长，直到耗尽系统内存
	 */
	public static void main(String[] args) throws Exception{
		//System.out.println(Runtime.getRuntime().availableProcessors());
		BlockingQueue<Runnable> queue = 
				//new LinkedBlockingQueue<Runnable>();
				new ArrayBlockingQueue<Runnable>(10);
		ExecutorService executor  = new ThreadPoolExecutor(
					5, 		//core
					10, 	//max
					120L, 	//2fenzhong
					TimeUnit.SECONDS,
					queue);
		
		for(int i = 0 ; i < 20; i++){
			executor.execute(new UseThreadPoolExecutor2());
		}
		Thread.sleep(1000);
		System.out.println("queue size:" + queue.size());		//10
		Thread.sleep(2000);
	}


}
