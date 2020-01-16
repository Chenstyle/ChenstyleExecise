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

