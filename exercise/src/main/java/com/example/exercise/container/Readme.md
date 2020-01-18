# 高并发容器的总结

## 1.对于Map/Set的选择使用

### 不加锁
- HashMap
- TreeMap
- LinkedHashMap

### 加锁-并发性不是特别高的情况下
- Hashtable
- Collections.synchronizedXXX
> 接收一个Map，返回为一个加锁的Map

### 并发性比较高
- ConcurrentHashMap
- ConcurrentSkipListMap
> 要求排序的情况

## 2.在使用队列的情况下

### 不加锁
- ArrayList
- LinkedList

### 加锁
Collections.synchronizedXXX

### 在高并发的情况下可以使用两种队列（Queue）
- ConcurrentLinkedQueue
- CopyOnWriteList
> 内部加锁
- BlockingQueue
> 阻塞式队列：
> LinkedBlockingQueue(无界队列，大小是机器的内存大小)
> ArrayBlockingQueue
> TransferQueue
> SynchronousQueue
- DelayQueue
> 执行定时任务 比如两小时后取消订单



