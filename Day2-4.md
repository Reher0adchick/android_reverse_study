# Day2-4 ndk开发学习，arm汇编指令学习

## 参考资料

> [Java层逆向分析方法和技巧 - 掘金 (juejin.cn)](https://juejin.cn/post/7197882841026314300)
> 

> [[原创]native层逆向分析(上篇)-Android安全-看雪-安全社区|安全招聘|kanxue.com](https://bbs.kanxue.com/thread-276068.htm)
> 

## java层逆向分析学习

这个看看文章就好，之前有做过一些安卓题目，还是比较熟悉的

## native层逆向分析学习

在androidstudio里面 setting里面可以安装安卓ndk，或者从官网下载都可以

new一个project，选择native c++

- java代码中声明native方法
- java代码在`静态语句块`里load动态库

### 安卓NDK开发学习

通过androidstudio是可以自动生成模板文件的，这个模板文件是可以直接用的

> [Android Studio 4.0.+NDK项目开发详细教学_android studio ndk开发_luo_boke的博客-CSDN博客](https://blog.csdn.net/luo_boke/article/details/107306531)
> 

如果想要自己灵活配置的话

可以参考这一篇文章，不过和androidstudio生成的没什么太大差别。

> [NDK<第一篇>：NDK集成开发流程 - 简书 (jianshu.com)](https://www.jianshu.com/p/583e91462173)
> 

后面学习一下用so加密的时候再回头来深化一下。

### ARM汇编指令学习

过了一遍这篇文章，等到实战的时候遇到看不懂的再来差文档好了

> [常用ARM指令集及汇编 (ntu.edu.tw)](https://www.csie.ntu.edu.tw/~b97079/arm/ARM_zhiling.pdf)
>