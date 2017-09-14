线程间通信：
	使用wait/notify方法实现线程间的通信，注意这是object的方法
	1.wait/notify必须配合synchronized关键字使用
	2.wait方法释放锁，notify不释放锁