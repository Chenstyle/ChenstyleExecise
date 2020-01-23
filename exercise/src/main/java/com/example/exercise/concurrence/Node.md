### synchronized
- synchronized之前我记错了。synchronized锁的是一个代码块，如果是写在方法的声明中，说明锁是这个对象的锁（等同于synchronized(this)），但是受影响的仅为这个方法。而不是整个对象。
- synchronized锁定的不是这块代码，而是在执行代码的时候锁定这个对象。
```
private int count = 10;
    private Object o = new Object();

    public void m() {
        synchronized (o) { // 任何线程要执行下面的代码，必须先拿到o的锁
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
```
- synchronized如果写在一个静态方法上，相当于锁的是这个类的class对象
```
private static int count = 10;

    public static synchronized void m() { // 这里等同于synchronized(e.004.T.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
```