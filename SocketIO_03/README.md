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