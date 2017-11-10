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
		