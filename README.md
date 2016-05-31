# xmai_fast_android
Android整合其他第三方，快捷开发框架

自定义快速开发框架，既添加了个人在工作中一些代码的总结，同时也是将目前的一些流行框架的一个整合。

开发环境：

Android studio

架构模式：

MVVM（简单化）

集成第三方框架：

Butterknife（利用APT实现注解）、GreenDao（高效ORM解决方案）、retrofit和okhttp（http访问）、glide(图片显示)、RxJava（响应式编程）、

目前的计划：

1、工具类：

（1）加入bugly，监测程序的crash问题

（2）加入Log控制--Mlog

（3）加入管理Activity工具--XAppMnager

（4）加入网络判断--NetUtils

（5）加入Sharepreferences工具--SharePreferenceUtils

2、基础类：

（1）加入BaseActivity或BaseFragment，提供showToast,progressDialog,startActivity等快捷方法。

（2）加入BaseAdapter，解决adapter重复写到想吐的囧题，分别提供recyclerview、listview和viewpager的解决方案

（3）加入BaseViewDelegate，基础视图的解决方案。

（4）加入纯展示基础activity
