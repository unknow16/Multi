package com.bjsxt.height.concurrent017;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UseExecutors {

	public static void main(String[] args) {
		
		//ExecutorService pool = Executors.newSingleThreadExecutor()
		
		//cache fixed single
		Executors.newFixedThreadPool(1);
		Executors.newSingleThreadExecutor();
		Executors.newCachedThreadPool();
		Executors.newScheduledThreadPool(10);
		
		
	}
}
