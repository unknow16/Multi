package com.bjsxt.height.design015.my;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

	//1.承装任务的集合
	private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<Task>();
	
	//2.承装workers的集合
	private Map<String, Thread> workers = new HashMap<String, Thread>();
	
	//3.承装每一个worker的执行结果集
	private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * master 掌控worker的创建
	 * worker是一个实现Runnable接口的对象，可供多个线程同时使用并行执行
	 * @param worker
	 * @param workerCount 线程数
	 */
	public Master(Worker worker, int workerThreadCount) {
		
		//向worker 传递任务队列，执行结果引用
		worker.setTaskQueue(taskQueue);
		worker.setResultMap(resultMap);
		
		//创建执行worker的线程，添加map，等待master启动
		for(int i=0; i<workerThreadCount; i++) {
			workers.put("worker_id_" + i, new Thread(worker));
		}
	}


	/**
	 * 提交任务
	 * @param task
	 */
	public void submit(Task task) {
		this.taskQueue.add(task);
	}

	/**
	 * 开始执行任务
	 */
	public void execute() {
		for(Map.Entry<String, Thread> entry : workers.entrySet()){
			entry.getValue().start();
		}
	}
	
	/**
	 * 判断worker线程是否执行完成
	 * @return
	 */
	public boolean isComplete() {
		for(Map.Entry<String, Thread> entry : workers.entrySet()) {
			if(entry.getValue().getState() != Thread.State.TERMINATED) return false;
		}
		return true;
	}


	public int getResult() {
		int result = 0;
		for(Map.Entry<String, Object> entry : resultMap.entrySet()) {
			result += (Integer) entry.getValue();
		}
		return result;
	}
}
