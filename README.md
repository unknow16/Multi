# 多线程并发编程
## Multi_01
### 线程安全概念
* 当多个线程访问某一个类（对象或方法）时，这个对象始终都能表现出正确的行为，那么这个类（对象或方法）就是线程安全的。
* synchronized：可以在任意对象及方法上加锁，而加锁的这段代码称为"互斥区"或"临界区"

### 一个实例多线程
* 实例方法加 synchronized（对象锁）
* 对象锁的同步和异步问题
* 同步的概念就是共享，如果不是共享的资源，就没有必要进行同步
* 异步的概念就是独立，相互之间不受制约
* 一个方法不加synchronized就是异步，同时执行，加了就是同步，所有要访问这个方法，就要排队依次执行
* 同步的目的就是为了线程安全，对于线程安全来说，需要满足两个特性：
* 	原子性（同步）
*   可见性

* t1线程先持有object对象的Lock锁，t2线程可以以异步的方式调用对象中的非synchronized修饰的方法
* t1线程先持有object对象的Lock锁，t2线程如果在这个时候调用对象中的同步（synchronized）方法则需等待，也就是同步

### 多个实例多线程
* 静态方法加 synchronized（类锁）

### service
* 业务整体需要使用完整的synchronized，保持业务的原子性。

### 锁重入
* synchronized的重入
* 也就是当一个线程得到了一个对象的锁后，
* 再次请求此对象时是可以再次得到该对象的锁

### 锁释放
* 出现异常，锁自动释放                                     
* 对于web应用程序，异常释放锁的情况，                            
* 如果不及时处理，很可能对你的应用程序业务逻辑产生严重的错误                  
* 比如你现在执行一个队列任务，很多对象都在等待第一个对象正确执行完毕再去释放锁，        
* 但是第一个对象由于异常的出现，导致业务逻辑没有正确执行完毕，就释放了锁，           
* 那抹可想而知后续的对象执行的都是错误的逻辑，这点需要考虑              
* catch Exception:后面逻辑会继续执行   
* catch InterruptedException/RuntimeException 会打断此后的逻辑     

### others
* 锁修改，字符串作为锁，相当于类锁, 因为String常量池的缓存功能，在同步代码块内修改字符串值，即释放锁，其他线程可进入
* 死锁
* 同一对象属性的修改不会影响锁的情况
* 使用synchronized代码块减小锁的粒度，提高性能

## Multi_02

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
 
