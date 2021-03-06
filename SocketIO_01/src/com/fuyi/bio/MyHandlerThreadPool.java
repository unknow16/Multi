package com.fuyi.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyHandlerThreadPool {

	private ExecutorService executorService;

	public MyHandlerThreadPool(int maxPoolSize, int queueSize) {
		executorService = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(), //核心线程数 
				maxPoolSize, //最大线程数
				120L, TimeUnit.SECONDS, //空闲回收时间 
				new ArrayBlockingQueue<Runnable>(queueSize)); //大于核心线程数，存入队列
	}

	public void execute(Runnable task) {
		executorService.execute(task);
	}
}
