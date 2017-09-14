# 多线程并发编程

## Multi_05
### JDK Executor框架
* Executors扮演线程工厂的角色，通过其可创建特定功能的线程池
* newFixedThreadPool()
* newSingleThreadExecutor()
* newCachedThreadPool()
* newScheduledThreadPool()
	
		`ThreadPoolExecutor pool = new ThreadPoolExecutor(
			1, 				//coreSize
			2, 				//MaxSize
			60, 			//60
			TimeUnit.SECONDS, 
			new ArrayBlockingQueue<Runnable>(3)			//指定一种队列 （有界队列）
			//new LinkedBlockingQueue<Runnable>()
			, new MyRejected()
			//, new DiscardOldestPolicy()
		);`
		
* 在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
若大于corePoolSize，则会将任务加入队列，
若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
若线程数大于maximumPoolSize，则执行拒绝策略。或其他自定义方式。
### 拒绝策略：
* AbortPolicy:直接抛出异常组织系统正常工作
* CallerRunsPolicy:只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务
* DiscardOldestPolicy:丢弃最老的一个请求，尝试再次提交当前任务
* DiscardPolicy:丢弃无法处理的任务，不给任何处理
如果需要自定义拒绝策略可以实现RejectedExecutionHandler接口 

* 使用无界队列时，当有新任务到来时，系统的线程数小于corePoolSize时，则新建线程执行任务，
达到corePoolSize后，就不会继续增加，
若后续仍有新的任务加入，而有没有空闲的线程资源，则任务直接进入队列等待，
若任务创建和处理的速度差异很大，无界队列会保持快速增长，直到耗尽系统内存
		 
### Concurrent.util工具类
#### CyclicBarrier
* 每个线程代表一个跑步运动员，当运动员都准备好后，才一起出发，只要一个人没准备好，大家都等待
#### CountDownLacth
* 经常用于监听某些初始化操作，等初始化执行完成后，通知主线程继续工作
#### Callable和Future
* Future模式非常适合在处理很耗时很长的业务逻辑时进行使用，可以有效的减小系统的响应时间，提高系统的吞吐量
#### Semaphore
* 信号量
#### ReentrantLock
* 重入锁，类似于synchronized
#### ReadWriteLock
* 读写锁，使用于读多写少性能优于重入锁
* 其核心是实现读写分离的锁
* 不同于synchronized\ReentrantLock，同一时间内，只能有一个线程进行访问被锁定的代码，读写锁则不同，其本质是分成两个锁，即读锁、写锁。在读锁下，多个线程可以并发的进行访问，但在写锁的时，只能一个个顺序访问。
* 口诀：读读共享，写写互斥，读写互斥

## Disruptor并发框架
### 简介
* Martin Fowler在自己网站上写了一篇LMAX架构的文章，在文章中他介绍了LMAX是一种新型零售金融交易平台，它能够以很低的延迟产生大量交易。这个系统是建立在JVM平台上，其核心是一个业务逻辑处理器，它能够在一个线程里每秒处理6百万订单。业务逻辑处理器完全是运行在内存中，使用事件源驱动方式。业务逻辑处理器的核心是Disruptor。
* Disruptor它是一个开源的并发框架，并获得2011 Duke’s 程序框架创新奖，能够在无锁的情况下实现网络的Queue并发操作。
* Disruptor是一个高性能的异步处理框架，或者可以认为是最快的消息框架（轻量的JMS），也可以认为是一个观察者模式的实现，或者事件监听模式的实现。
* 推荐学习网站：http://ifeve.com/disruptor-getting-started/
 
