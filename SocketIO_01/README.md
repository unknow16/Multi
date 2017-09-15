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