## Android应用内升级App
这是一个慕课网课程练习Demo，[课程地址](https://www.imooc.com/learn/1168)

本项目主要是练习对于OkHttp的网络框架封装，接口隔离。

对于下载更新的校验，安装，系统适配。

### 还需要加入的内容

1.在用户下载之前检查本地是否存在已经下载好的文件，使用md5校验，如果校验成功，直接提示安装，避免重复下载。

2.可以支持断点续传功能，分段多线程下载Apk
> Http的header里面有一个range属性，它可以指定下载一个文件的起始字节和终止字节
>
> 比如一个文件有300个字节，我们可以启用3个线程。线程1请求1-100字节，线程2请求101-200，线程3请求201-file.length。
>
> 我们只要在RANGE里面带上这样的字节区间，Server会返回对应的这样一段的数据。
>
> 这样就为我们提供了一个可能：我们拿到了一个文件以后，启动多个线程，为每一个线程去划分一定的区间，并行去执行。等待全部都下载完成以后，合并一下。
>
> 使用RandomAccessFile的seek方法，可以拖到指定的位置去写字节。
>
> 所以断点续传就两个核心的功能，一个是HTTP它本身是支持的，另外本地通过RandomAccessFile.seek到一个指定的位置去写，来合并整个文件。

3.可以考虑做增量更新
> 先在Server端把用户安装的APK和本次更新的APK做比对，把不同的部分生成一个patch，然后download这个patch，和本地的apk1做合并，生成一个新的apk2来安装。
>
> Apk1 本地
> Apk2 server
> Apk1 diff Apk2 -> patch
> download patch , Apk1 + patch -> Apk2
>
> 主要涉及到一个叫bsdiff的开源算法。

### 项目中体现的知识点
1.做一个独立功能的时候，我们可以对外去提供一个使用的类。比如：[AppUpdater](https://github.com/Chenstyle/WechatMainInterface/blob/master/appupdater/src/main/java/com/example/appupdater/updater/AppUpdater.java)

2.需要用到的实现模块，比如说[网络模块](https://github.com/Chenstyle/WechatMainInterface/tree/master/appupdater/src/main/java/com/example/appupdater/updater/net)。需要考虑通过接口去屏蔽具体的实现。

3.如果使用的是https的接口，可能会遇到错误。比如说OkHttp默认不能访问自签名的https网站，需要做一些证书的配置；

4.使用DialogFragment去替代直接使用Dialog.

5.Apk 安装。在Android N 的时候要做 FileProvider的适配，O的时候要做 Install permission

6.Android P禁止直接访问Http的接口，所以要加一个network_security_config

7.做网络请求的时候要考虑cancel

