### 多线程中的并行设计模式：
#### Future：
* 类似场景商品订单，提交订单后，只用在家等待送货上门，
* 像ajax，无需一直等待请求结果，可以做其他事情
* Future 表示异步计算的结果。

#### Master-Worker：
* master分配和调度任务，worker服务执行子任务 
* 生产者-消费者模式：若干个生产者线程，若干个消费者线程