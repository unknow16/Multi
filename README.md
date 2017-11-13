## 目录
* 多线程并发编程
* Disruptor并发框架
* NIO
* Netty并发网络框架

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
### 线程间通讯
* 使用wait/notify方法实现线程间的通信，注意这是object的方法
* 当使用wait 和 notify 的时候 ， 一定要配合着synchronized关键字去使用
* wait() 释放lock锁， 等待。。
* notify() 唤醒其他线程，但不释放锁，继续执行完成再释放

### CountDownLatch
* countDownLatch.await(); 释放锁，等待
* countDownLatch.countDown(); 放行awit()阻塞的线程

## Multi_03
### 同步类容器：
* 状态串行化，虽实现了线程安全，但严重降低了并发性，在多线程环境中，严重降低了程序的吞吐量

### 并发类容器：
* jdk5.0以后提供了并发类容器，专为并发设计
* ConcurrentHashMap <== HashTable
* CopyOnWriteArrayList <== Voctor
* CopyonWriteArraySet
* 并发的Queue: ConcurrentLinkedQueue\LinkedBlockingQueue
	
#### ConcurrentMap接口
* ConcurrentHashMap 最高支持16个段 segment，细粒度同步
* ConcurrentSkipListMap 支持并发排序类似treeMap
		
#### Copy-On-Write容器   
* 适用于读多写少CopyOnWriteArrayList CopyOnWriteArraySet
* 写时复制，指当往一个容器中做写（add,del,update）操作时，不直接往当前容器中添加
* 而是将当前容器进行copy，复制出一个新的容器，然后对容器进行写操作，
* 完成之后，再将原容器的引用指向新的容器，
* 这样做可以对CopyOnWrite容器进行并发的读，而不需要加锁，
* 提现读写分离的思想，读和写不同的容器、
		
#### ReentrantLock 重入锁
	
#### ConcurrentLinkedQueue
* 通过无锁的方式实现，基于链接节点的无界线程安全队列，先进先出,性能最好	
			
#### BlockingQueue(阻塞队列)	
* ArrayBlockingQueue:有界队列
* LinkedBlockingQueue：无界
* SynchronousQueue:一种没有缓冲的队列，生产者产生的数据直接会被消费者获取并消费
* PriorityBlockingQueue：其中的元素必须实现Comparable接口，无界
* DelayQueue:带有延迟时间的queue,无大小限制的队列
			其中的元素只有当其指定的延迟时间到了，才能从队列中获取到该元素
			其中的元素需实现Delayed接口，
			应用场景：对缓存超时的数据进行移除，任务超时时处理，空闲连接的关闭等。

## Multi_04
### 多线程中的并行设计模式：
#### Future：
* 类似场景商品订单，提交订单后，只用在家等待送货上门，
* 像ajax，无需一直等待请求结果，可以做其他事情
* Future 表示异步计算的结果。

#### Master-Worker：
* master分配和调度任务，worker服务执行子任务 
* 生产者-消费者模式：若干个生产者线程，若干个消费者线程
		

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

## IO(BIO)和NIO
* 本质是阻塞与非阻塞的区别
* 阻塞：应用程序在获取网络数据的时候，如果网络传输数据很慢，那抹客户端程序就一直等着，直到传输完毕，才继续执行后面的代码。
* 非阻塞：应用程序直接可以获取已经准备好的就绪的数据，无需等待

### BIO为同步阻塞形式
### NIO为同步非阻塞形式，并没有实现异步
### NIO2.0(AIO)，即在JDK1.7后，升级了NIO库包，支持异步非阻塞通讯模型

### 同步\异步
* 同步和异步一般是面向操作系统与应用程序对IO操作的层面上来区别的
* 同步时：应用程序会直接参与IO读写操作，并且我们的程序会直接阻塞到某个方法上，直到数据准备就绪，或者采用轮询的策略实时检查数据的就绪状态，如果就绪则获取数据
* 异步时，则所有的IO读写操作交给操作系统处理，与我们的应用程序没有直接关系，我们的程序不关心IO读写，当操作系统完成IO读写操作时，会给我们应用程序发送通知，我们的程序直接拿走数据即可
* 同步说的是你的server服务器的执行方式
* 阻塞说的是具体的技术，接受数据的方式、状态（io,nio）

## NIO 1.0
### Buffer(缓冲区)
### Channel(管道，通道)
* 网络数据通过Channel读取和写入，通道与流的不同之处在于通道是双向的，而流只是一个方向上移动（一个流必须是InputStream或者OutputStream的子类）
* 通道可以用于读、写或二者同时进行，关键可以和selector多路复用结合起来，有多种的状态位，方便多路复用器去识别
* 分为两类，一是网络读写的SelectableChannel,一是用于文件操作的FileChannel
* SocketChannel和ServerSocketChannel都是SelectableChannel的子类
### Selector(选择器，多路复用器)
* 提供选择已经就绪的任务的能力
* 会不断轮询注册在其上的通道Channel，如果某个通道发生了读写操作，这个通道就处于就绪状态，会被Selector轮询出来，然后通过SelectionKey可以取的就绪的Channel集合，从而进行后续的IO操作
* 一个多路复用器Selector可以负责成千上万channel通道，没有上限，这也是JDK使用了epoll（linux内核中的一种机制）代替传统的select实现，获得连接句柄没有限制，这也就意味着我们只要一个线程负责selector的轮询，就可以连接成千上万个客户端
* selector线程就类似一个管理者Master，管理了成千上万个管道，然后轮询那个管道的数据已经准备好，通知cpu执行io的读取或写入操作
* selector模式，当io事件（管道）注册到选择器以后，selector会分配给每个管道一个key，相当于标签，selector选择器是以轮询的方式进行查找注册的所有io事件（管道），当我们的io事件（管道）就绪后，selector就会识别，会通知key值来找到相应的管道，进行相关数据处理操作（从管道里读或写数据，写到我们的数据缓冲区中）
* 每个管道都会对选择器进行注册不同的事件状态，以便选择器查找
	`SelectionKey.OP_CONNECT
	SelectionKey.OP_ACCEPT
	SelectionKey.OP_READ
	SelectionKey.OP_WRITE`

## NIO 2.0
### AsynchronousChannelGroup
### AsynchronousServerSocketChannel

## Netty
* Hadoop的RPC框架Avro，JMS，RocketMQ，分布式通讯框架Dubbox底层都采用netty实现
* 服务器端tcp内核模块维护有2个队列，可称为A,B
客户端向服务器connect时，会发送带有SYN标志的包（第一次握手）
服务器收到客户端发来的SYN后，会想客户端发送SYN ACK确认（第二次握手）
此时TCP内核模块把客户端链接加入到A队列中，然后服务器收到客户端发来的ACK时（第三次握手）
TCP内核模块会把客户端链接从A队列移到B队列，链接完成，应用程序的accept会返回
也就是说accept从B队列中取出完成三次握手的链接

##TCP粘包、拆包问题 
* 解决方案，根据业界主流协议，有三种方案：
	* 1.消息定长，例如每个报文的大小固定为200个字节 ，如果不够，空格补位
	* 2.在包尾部增加特殊字符进行分割，例如加回车
	* 3.将消息分为消息头和消息体，在消息头中包含表示消息总长度的字段，然后进行业务逻辑的处理

* netty提供前两种：
	* 分隔符类DellmiterBasedFrameDecoder(自定义分隔符)
	* FixedLengthFrameDecoder(定长)

## 编解码技术
* 说白了就是java序列化技术，序列化目的就是两个，第一进行网络传输，第二对象持久化
* 虽然我们可以使用java进行对象序列化，netty去传输，但是java序列号的硬伤太多，比如java序列化没法跨语言、序列化后码流太大、序列化性能太低等等
* 主流的编解码框架：
	* JBoss的Marshalling包
	* Google的Protobuf
	* 基于Protobuf的Kyro
	* MessagePack框架
 
## netty最佳实践
### 相互通讯
* 参数设置都是根据服务器性能决定的
* 我们考虑的是两台机器使用netty怎样通讯
	* 1.使用长连接通道不断开的形式进行通讯，也就是服务器和客户端的通道一直处于开启状态，如果服务器性能足够好，并且客户端也相对较少的情况下，推荐使用
	* 2.一次性批量提交数据，采用短链接方式，也就是我们会把数据保存在本地临时缓冲区或者临时表中，当达到临界值时进行一次性批量提交，又或者根据定时任务轮询提交，这种情况弊端是做不到实时性传输，在对实时性不高的应用程序中可以推荐使用
	* 3.我们可以使用一种特殊的长链接，在指定的某一时间内，服务器与某台客户端没有任何通讯，则断开链接，下次链接则是客户端向服务器发送请求时，再次建立链接，需要考虑2点
		* 1.如何超时后关闭通道？关闭通道后我们又如何再次建立链接？ReadTimeoutHandler
		* 2.客户端宕机时，无需考虑，服务器宕机时，我们的客户端如何与服务器进行链接呢？

### 心跳监控

## http协议响应状态码
* 1xx:提示信息，表示请求已经接受继续处理
* 2xx:成功，表示请求已经接受成功
* 3xx:重定向，要完成的请求必须进行更进一次的操作
* 4xx:客户端错误，可能是请求语法错误或者请求无法实现
* 5xx:服务端错误，服务器未能处理请求（可能内部出现异常）

### 常见状态码
* 200 OK 成功
* 400 Bad Request 错误的请求语法，不能被服务器理解
* 401 Unauthorized: 请求未经授权
* 403 Forbidden:服务器收到请求，但请求被服务器拒绝
* 404 Not Found:请求的资源不存在
* 405 Method Not Allowed:请求方式不被允许，如只支持get请求，但客户端使用了post请求
* 500 Inernal Server Error:服务器发送不可预期的错误
* 503 Server Unavailable: 服务器当前不能处理客户端请求，一段时间后可能恢复正常
