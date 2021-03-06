package com.bjsxt.height.design015.my;

public class Main {

	public static void main(String[] args) {
		
		//Runtime.getRuntime().availableProcessors(); 当前运行时处理器核心数
		Master master = new Master(new Worker(), 10);
		
		for(int i=0; i<10; i++) {
			Task task = new Task();
			task.setId(i);
			task.setName("task_name=" + i);
			
			master.submit(task);
		}
		
		master.execute();
		long start = System.currentTimeMillis();
		
		while(true) {
			if(master.isComplete()){
				Integer result = master.getResult();
				long end = System.currentTimeMillis() - start;
				System.out.println("结果：" + result);
				System.out.println("执行时间：" + end);
				break;
			}
		}
	}
}
