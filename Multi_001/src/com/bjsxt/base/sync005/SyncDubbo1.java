package com.bjsxt.base.sync005;
/**
 * synchronized的重入
 * 也就是当一个线程得到了一个对象的锁后，
 * 再次请求此对象时是可以再次得到该对象的锁
 * @author alienware
 *
 */
public class SyncDubbo1 {

	public synchronized void method1(){
		System.out.println("method1..");
		method2();
	}
	public synchronized void method2(){
		System.out.println("method2..");
		method3();
	}
	public synchronized void method3(){
		System.out.println("method3..");
	}
	
	public static void main(String[] args) {
		final SyncDubbo1 sd = new SyncDubbo1();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				sd.method1();
			}
		});
		t1.start();
	}
}
