JDK多任务执行Executor框架
	Executors扮演线程工厂的角色，通过其可创建特定功能的线程池
	newFixedThreadPool()
	newSingleThreadExecutor()
	newCachedThreadPool()
	newScheduledThreadPool()
	
	ThreadPoolExecutor pool = new ThreadPoolExecutor(
			1, 				//coreSize
			2, 				//MaxSize
			60, 			//60
			TimeUnit.SECONDS, 
			new ArrayBlockingQueue<Runnable>(3)			//指定一种队列 （有界队列）
			//new LinkedBlockingQueue<Runnable>()
			, new MyRejected()
			//, new DiscardOldestPolicy()
		);
		/**
		 * 在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
		 * 若大于corePoolSize，则会将任务加入队列，
		 * 若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
		 * 若线程数大于maximumPoolSize，则执行拒绝策略。或其他自定义方式。
		 * JDK拒绝策略：
		 		AbortPolicy:直接抛出异常组织系统正常工作
		 		CallerRunsPolicy:只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务
		 		DiscardOldestPolicy:丢弃最老的一个请求，尝试再次提交当前任务
		 		DiscardPolicy:丢弃无法处理的任务，不给任何处理
		 		如果需要自定义拒绝策略可以实现RejectedExecutionHandler接口 
		 */	
		
		/**
		 * 使用无界队列时，当有新任务到来时，系统的线程数小于corePoolSize时，则新建线程执行任务，
		 * 达到corePoolSize后，就不会继续增加，
		 * 若后续仍有新的任务加入，而有没有空闲的线程资源，则任务直接进入队列等待，
		 * 若任务创建和处理的速度差异很大，无界队列会保持快速增长，直到耗尽系统内存
		 */
		 
Concurrent.util工具类
	CyclicBarrier：每个线程代表一个跑步运动员，当运动员都准备好后，才一起出发，只要一个人没准备好，大家都等待
	CountDownLacth: 经常用于监听某些初始化操作，等初始化执行完成后，通知主线程继续工作
	

重入锁、读写锁