## ViewPager+Tab特效实现微信主界面

来自慕课网同名教程：[ViewPager+Tab特效实现微信主界面](https://www.imooc.com/learn/1116)

简介：本课程以微信主界面为例，介绍如何使用ViewPager和FragmentPagerAdapter实现多页面滑动效果，底部Tab的指示器动画特效，还将扩展ViewPager实现透明度渐变、旋转等特效。

### 笔记

#### 第一点：Fragment.newInstance()

大家在使用Fragment的时候，一定要通过这种newInstance的方式，利用Arguments去保存关键的数据。千万不要直接new一个Fragement，然后通过方法去设置Title。要合理的使用newInstance.

原因是因为当App在后台进入Pause状态，切到前台之后生命周期会进行重调，但因为是用的方法去设置参数，参数并没有根据Fragment的生命周期进行恢复，导致参数不正确，出现bug。严重可能会导致生产环境错误，造成损失。

#### 第二点：FrgamentPagerAdapter和FragmentStatePagerAdapter

```
FragmentPagerAdapter
    onDestoryView
    onCreateView
    
    Fragment并没有被销毁

FragmentStatePagerAdapter
    onDestoryView
    onDestory
    onCreate
    onCreateView
    
    fragment被销毁
```
如果是只有4个Tab，使用FragmentPagerAdapter;

100个Tab，图片预览，FragmentStatePagerAdapter

#### 第三点：Activity与Fragment的通信

Activity调用Frgament的方法很简单，我们只要拿到Fragment的方法去执行就可以了。

Fragment调用Activity的方法，我们要换个思路：**不是Fragment要调用Activity的方法，而是Fragment对外提供自己的核心事件回调，Activity自己hi选择是否监听。**

因为Fragment它是一个复用单元，Frgament本身并不知道哪个Activity会使用它，或者说未来哪个Activity会使用它，所以呢，不是Fragment要调用Activity的某一个方法，而是Fragment要对外提供自己的一些核心事件的回调。Activity爱监听不监听，监听了以后Activity自己调用自己的方法。

#### 第四点：在ViewPager的情况下，如何正确的去管理多个Fragment

在视频中我们演示了一种错误的写法，在屏幕旋转后，onCreate()会重新执行，getItem()没有执行。会导致获取的Fragment与现实的Fragment对不上，所以如何正确的管理多个Tab的Fragemnt，大家一定要重视一下。

#### 第五点：Tab自定义控件

自定义控件有两种方式：**纯绘制**和**组合形式**采用纯绘制效率会高一点，但是很有可能许多细节考虑不到，会出现bug。采用组合的方式稳定性会稍微好一些，因为都是系统封装好的控件上开发。

一般情况下在一些嵌套比较深，或者是需要专去做优化的时候，会采用绘制的方式来替换掉一些组合方式的自定义View。在实际开发过程中，组合的方式用的比较多。

本课程是以组合形式完成了一个[TabView](https://github.com/Chenstyle/WechatMainInterface/blob/master/app/src/main/java/com/example/wechatframe/view/TabView.java)。

在本教程中，不管是使用绘制还是组合，对外提供的方法都会是一致的。都要设置Icon，text,progress

#### 第六点：Tab变色

这里老师希望能够举一反三，具体指的是ViewPager能提供一个0~1的回调，Tab就可以根据这个回调去做很多事情。比如要做一个进度的效果，就可以根据这个值去做。大多数的Tab动画都能够以这样的思路去解决掉。

#### 第七点：Activity旋转不能忽视

并不是说Activity一定要支持旋转，而是我们可以利用Activity的旋转去测试当系统回收Activity时候的表现。不要求旋转的时候UI完美，功能上的问题不应该有。可以利用Activity或者Fragment本身的onSaveStateInstance()和onCreate()去做一些恢复的操作。