### 线程池基础
- Executor
> 用来执行某一个任务，只有一个execute方法
- ExecutorService
> 除了execute方法外，还有一个submit方法。submit可以执行Runnable和Callable
- Callable
> 和Runnable非常相似，但是有返回值
- Executors
> 操作上面那些的工具类
- ThreadPool
> 线程池
- Future
> 未来的，返回值 通常和Callable, Executor, ThreadPool结合在一起使用。

### 6种线程池
- Fixed
- Cache
- Single
- Scheduled
- ForkJoin
- WorkStealing
> 和ForkJoin本质上是一种，是在ForkJoin的基础上做了一个更高层的抽象

### 线程池底层的实现
- ThreadPoolExecutor
> 通过这个类可以自定义线程池

### ParallelStreamAPI
> 多线程计算
