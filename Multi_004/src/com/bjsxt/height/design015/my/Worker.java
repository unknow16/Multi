package com.bjsxt.height.design015.my;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable {
	
	//承装任务的集合
	private ConcurrentLinkedQueue<Task> taskQueue;
	
	//承装每一个worker的执行结果集
	private ConcurrentHashMap<String, Object> resultMap;
	

	@Override
	public void run() {
		while(true) {
			Task input = taskQueue.poll();
			if(input == null) break;
			
			//真正的执行业务处理
			Object output = handle(input);
			
			resultMap.put(Integer.toString(input.getId()), output);
		}
		
		
	}

	
	private Object handle(Task input) {
		Object output = null;
		try {
			Thread.sleep(1000);
			output = input.getId();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return output;
	}

	public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
		this.taskQueue = taskQueue;
	}


	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	
}
