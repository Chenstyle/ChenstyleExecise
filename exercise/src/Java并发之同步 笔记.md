# Java并发之同步性

本文档来自马士兵的直播[公开课教学录屏内容](https://www.bilibili.com/video/av33688545)。

### Synchronized 关键字
意思为加把锁，synchronized锁定的是对象，当我们把sysnchronized加在方法上面，sysnchronized锁定的是this对象，如果这个方法是static的，synchronized锁定的是类名.class对象，

同步的方法和非同步的方法可以同时调用，同步方法需要锁，非同步方法不需要锁。所以两个是不冲突的。

对业务写方法加锁，业务读方法不加锁，容易产生脏读问题。

一个同步方法可以调用另外一个同步的方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁，也就是说synchronized获得的锁是可重入的。

程序在执行过程中，如果出现异常，默认情况下锁会被释放。

### Volatile 关键字
使一个变量在多个线程间可见。
> A，B线程都用到一个变量，java默认是A线程中保存一份copy，这样如果在B线程中修改了该变量，则A线程未必知道。使用volatile关键字，会让所有线程都会读到变量的修改值。

> 备注：线程是操作系统能够运算调度的最小单位。A线程在运行的过程中，会将变量缓存到CPU缓存（CPU寄存器）中，此时B线程去修改变量的时候，A线程不可知。而在变量前加上volatile就可以解决这个问题。

volatile并不能保证多个线程共同修改变量所带来的不一致问题，也就是说volatile不能代替synchronized。volatile只能解决可见性问题，而synchronized既解决了可见性，又解决了原子性问题。

比较简单的自增自减，或者加一个数，减一个数这样一类的原子性问题。解决同样的问题更高效的方法，可以使用AtomXXX类来解决。如：AtomicInteger，AtomicLong...自己查阅API文档
> AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的。具体对应类型

synchronized优化：同步代码块中的语句越少越好。

锁定某对象o，如果o的属性发生改变，不影响锁的使用。但是如果o变成另外一个对象，则锁定的对象发生改变，应该避免将锁定对象的引用变成另外的对象。
> 锁不是锁在栈的空间里，而是锁在堆的空间里。锁的信息是记录在new出来那个对象内部的。

不要以字符串常量作为锁定对象，会一不小心产生死锁的问题。
> 因为String val = "word";如果锁定了val，那只要在项目中同样也出现对"word"进行锁定的代码，对String来说，其实是指向的是同一个内存空间。两个线程锁同一个对象，当没有进行明确的调配的时候，就会产生死锁。

### 通过wait、notify让线程进行通讯
两个线程之间要通讯的时候记住一点：wait是释放锁的，但是notify是不释放锁的。既然wait是释放锁的，那就说明你要想调用wait方法，你必须得得到这把锁才行。所以wait、notify必须在synchronized里面调用。

首先锁定，然后wait释放锁，notify不释放锁，要想互相配合的比较好，notify完了之后自己得再做wait才能释放当前线程拥有的这把锁。

如果后期掌握了同步器，能不用wait和notify就不要用。在java的多线程里面，拿wait和notify写东西，就相当于在用汇编编程一样。它能完成的功能很多，但是它用起来很麻烦，也需要非常的小心。

### 使用Latch（门闩）替代wait notify来进行通知
好处是通信方式简单，同时也可以指定等待时间。
使用amait和countdown方法替代wait和notify。
CountDownLatch不涉及锁定，当count的值为零时当前线程继续运行。
当不涉及同步，只是涉及线程通信的时候，用synchronized + wait/notify就显得太重了。
这时应该考虑CountDownLatch/CyclicBarrier/Semaphore

如果用CountDownLatch的话相当简单，就是个门闩。一个线程如果调用了CountDownLatch.wait()就在调用的地方等待，等待门闩打开。门闩打开的条件是CountDownLatch指定一个初始值，每调用一次CountDownLatch.countDown这个初始值就减一，直到这个初始值变成0的时候，门闩就开了。