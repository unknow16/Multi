package com.bjsxt.base.sync005;

import javax.management.RuntimeErrorException;

/**
 * 出现异常，锁自动释放
 * 对于web应用程序，异常释放锁的情况，
 * 如果不及时处理，很可能对你的应用程序业务逻辑产生严重的错误
 * 比如你现在执行一个队列任务，很多对象都在等待第一个对象正确执行完毕再去释放锁，
 * 但是第一个对象由于异常的出现，导致业务逻辑没有正确执行完毕，就释放了锁，
 * 那抹可想而知后续的对象执行的都是错误的逻辑，这点需要考虑
 * 
 * synchronized异常
 * @author alienware
 *
 */
public class SyncException {

	private int i = 0;	
	public synchronized void operation(){
		while(true){
			try {
				i++;
				Thread.sleep(100);
				System.out.println(Thread.currentThread().getName() + " , i = " + i);
				if(i == 10){
					Integer.parseInt("a");
					//throw new RuntimeException();
				}
			} catch (Exception e) { //Exception:后面逻辑会继续执行   InterruptedException 会打断此后的逻辑
				e.printStackTrace(); //根据任务之间是否有独立
				
				//throw new RuntimeException(e);
			}
		}
	}
	
	public static void main(String[] args) {
		
		final SyncException se = new SyncException();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				se.operation();
			}
		},"t1");
		t1.start();
	}
	
	
}
