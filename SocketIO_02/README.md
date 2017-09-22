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