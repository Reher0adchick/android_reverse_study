# Day 1 安卓目录结构学习

## 学习参考

> [Android应用加固原理 - 掘金 (juejin.cn)](https://juejin.cn/post/6844903952345989134#heading-11)
> 

> [Android逆向基础----APK文件结构 - 时光不改 - 博客园 (cnblogs.com)](https://www.cnblogs.com/zhaijiahui/p/6916556.html)
> 

关于github仓库管理的文档

> [创建仅含议题的仓库 - GitHub 文档](https://docs.github.com/zh/repositories/creating-and-managing-repositories/creating-an-issues-only-repository)
> 

## APK文件结构

| 文件夹 | 说明 |
| --- | --- |
| asset | 资源文件 |
| lib | native动态库so |
| META-INF | 1. 该目录下存放的是签名信息，用来保证apk包的完整性和系统的安全性 2. http://cert.rs/ 保存着该应用程序的证书和授权信息 3. CERT.SF 保存着SHA-1信息资源列表 4. MANIFEST.MF 清单信息 |
| res | 资源库 |
| AndroidManifest.xml | 一个清单文件，它描述了应用的名字、版本、权限、注册的服务等信息。 |
| classes.dex | java源码编译经过编译后生成的dalvik字节码文件，主要在Dalvik虚拟机上运行的主要代码部分 |
| resources.arsc | 编译后的二进制资源文件索引。 |
| smail | smail中间码 |

一般重点区域为lib里面的native库，会存放着java层调用的代码和函数

还有Manifest文件，这里面会存放apk的配置信息，Activity，Service，Provider和Receiver都在AndroidManifest.xml中注册。

传统的java虚拟机通过执行class文件运行，java码都被制成字节码录入class文件。

而Dalvik虚拟通过dex执行，java源码在打包的时候被AndroidSDK里面的dx工具将java码转换成dalvik字节码。dx工具可以合并重组多个class文件

## dex文件

dex是Android系统的可执行文件，包含应用程序的全部操作指令以及运行时数据。

![](D:\1_my_re_code\andriod_study\android_reverse_study\pic\Day1\Untitled.png)

### dex文件结构

| 数据名称 | 解释 |
| --- | --- |
| dex_header | dex文件头部记录整个dex文件的相关属性 |
| string_table | 字符串数据索引，记录了每个字符串在数据区的偏移量 |
| type_table | 类似数据索引，记录了每个类型的字符串索引 |
| proto_table | 原型数据索引，记录了方法声明的字符串，返回类型字符串，参数列表 |
| field_table | 字段数据索引，记录了所属类，类型以及方法名 |
| method_table | 类方法索引，记录方法所属类名，方法声明以及方法名等信息 |
| class_def | 类定义数据索引，记录指定类各类信息，包括接口，超类，类数据偏移量 |
| data_section | 数据区，保存了各个类的真是数据 |

那平常说的java层，native 层指的是什么呢？dex和so的区别又是什么呢

### 关于java层与native层

java层指的是运行在dalvik虚拟机上面的java代码，而native层指的是在c/c++环境下运行的代码。两者通过JNI（java naitve interface）交互

### 关于Dalvik虚拟机

这是用于安卓的java虚拟机，可以执行dex（dalvik execuable）格式的可执行文件

Dalvik字节码的阅读是很重要的事情，在apktool解包之后，smail字节码就是Dalvik字节码的反编译字节码。

## apktools 的使用

虽然说这个我早就掌握了，但是还是温习一下

```python
#进入apktool所在文件夹
解包
apktool d file
重新打包
apktool b file
keystone生成
keytool -genkey -alias abc.keystore -keyalg RSA -validity 20000 -keystore abc.keystore
签名命令
jarsigner -verbose -keystore abc.keystore -signedjar nfile file abc.keystore
```

不过此时的签名是v1的签名，要签v2，v3的签名需要用别的工具。我之前签过，但是安装失败了。。可能是有验证什么的。

一般使用Androidstudio生成的apk是可以用androidstudio自带的工具去签名

### apk打包流程

1. 打包资源(res/assets/AndroidManifest.xml/Android基础类库)文件
2. 生成 R.java和resources.ap_文件
3. 处理AIDL文件，生成对应的.java文件
4. 编译Java文件，生成对应的.class文件
5. 把.class文件转化成Davik VM支持的.dex文件(.java=>.class=>.dex)
6. 打包生成未签名的.apk文件
7. 对未签名.apk文件进行签名
8. 对签名后的.apk文件进行对齐处理

## Smail码学习

> [Android逆向基础：Smali语法 - 简书 (jianshu.com)](https://www.jianshu.com/p/9931a1e77066)
> 

### 基本数据类型

| smail | java |  |
| --- | --- | --- |
| V | void |  |
| Z | boolen |  |
| B | byte |  |
| S | short |  |
| C | char |  |
| I | int |  |
| J | long |  |
| F | float |  |
| D | double |  |
| Lpackage/name | 对象类型 | L表示这是一个对象类型，package表示该对象所在的包，；表示对象名称的结束 |
| [类型 | 数组 | [I表示一个int型数据，[Ljava/lang/String 表示一个String的对象数组 |

### 关键词学习

| 关键词 | 说明 |
| --- | --- |
| .class | 定义Java类名 |
| .super | 定义父类名 |
| .source | 定义java源文件名 |
| .filed | 定义字段 |
| .method | 定义方法开始 |
| .end method | 定义方法结束 |
| .annotation | 定义数注释开始 |
| .end annotation | 定义注释结束 |
| .implements | 定义接口指令 |
| .local | 指定方法内局部变量的个数 |
| .registers | 指定方法内使用寄存器总数 |
| .prologue | 表示方法中代码的开始处 |
| .line | 表示java源文件中指定行 |
| .paramter | 指定了方法的参数 |
| .param | 和上一个含义一致，但是格式不一样 |

### 寄存器

Dalvik的寄存器命名法为

1、本地寄存器

用v开头数字结尾的符号来表示，v0, v1, v2,...

2、参数寄存器

用p开头数字结尾的符号来表示，p0,p1,p2,...

*在非static方法中，p0代指this，p1为方法的第一个参数。*

*在static方法中，p0为方法的第一个参数。*

### 成员变量

成员变量定义格式为：

.field public/private [static][final] varName:<类型>

获取指令

iget, sget, iget-boolean, sget-boolean, iget-object, sget-object

操作指令

iput, sput, iput-boolean, sput-boolean, iput-object, sput-object

array的操作是aget和aput

指令解析

sget-object v0,Lcom/aaa;->ID:Ljava/lang/String;

获取ID这个String类型的成员变量并放到v0这个寄存器中

iget-object v0,p0,Lcom/aaa;->view:Lcom/aaa/view;

iget-object比sget-object多一个参数p0，这个参数代表变量所在类的实例。这里p0就是this

示例

```python
.local v0, args:Landroid/os/Message;
const/4 v1, 0x12
iput v1,v0,Landroid/os/Message;->what:I
```

### 函数

函数格式

.method public/private [static][final] methodName()<类型>

.end method

调用指令：

invoke-direct

invoke-virtual

invoke-static

invoke-super

invoke-interface

调用格式：

invoke-指令类型 {参数1, 参数2,...}, L类名;->方法名

如果不是是静态方法，参数1代表调用该方法的实例。

```
.method public constructor <init>()V
    .locals 0

    .line 8
    invoke-direct {p0}, Landroidx/appcompat/app/AppCompatActivity;-><init>()V

    return-void
.end method
```

定义一个公用方法，一个构造函数，初始化一个AppCompatActivity 的类的实例