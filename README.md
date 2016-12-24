# FrameExample-master说明
使用**rxjava+retorfit+mvp**快速开发APP.项目中包含网络框架、rxbus、数据存储、日志框架、MVP baseActivity baseFragment、常用控件等的封装。项目直接引入module使用。
项目UI使用： **material design**
本例子使用干货集中营提供的开放api为例 结合上面的框架进行开发 。干货集中营
api网址：http://gank.io/api

## 整体架构
rxjava+mvp+retorfit+MaterialDesign

## module说明
| 名称        | 功能   |
| --------   | :-----  |
| JNet     | 使用rxjava+retrofit对网络的封装 |
| JLog        |  android打印日志库   |
| JWidget        |    界面一些通用组件    |
| JUtil        |    常用的工具类    |

## module使用

### JNet
集成JNet暴露类Api方法说明：

 | 方法        | 说明   |
 | --------   | :-----  |
 | init(@NonNull String baseUrl1, @NonNull ProtocolType protocolType) | 此方法为必调方法，初始化api的url与协议类型(json或protobuf)。一般在Application里面初始化一次就行。 |
 | timeout(long timeout) | 连接超时(单位：MILLISECONDS)  |
 | addInterceptor(@NonNull Interceptor interceptor) | 增加一个过滤器  |

### JNet使用示例：
```
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化api
        Api.init("http://gank.io/api/data/" , ProtocolType.JSON);
        Api.get().addInterceptor(new NetInterceptor());
    }
}
```

### JLog说明
jlog可以打印普通json，list对象，map对象，String等，也可以定位到某一行等功能。
### JLog使用说明
集成JLog暴露类JLog方法说明：
JLog不需要任何初始化，直接在调用的地方使用其静态方法即可.如：
| 方法        | 说明   |
| -------| :-----  |
| JLog.v(@NonNull String message) |   打印version日志信息    |
| JLog.d(@NonNull String message) |   打印debug日志信息    |
| JLog.w(@NonNull String message) |   打印warn日志信息    |
| JLog.e(@NonNull String message)     |   打印error日志信息    |
| JLog.json(@NonNull String message)   |  打印json日志信息    |
| JLog.obj(@NonNull String message)  |   打印object(暂支持map,集合，数组)日志信息    |

效果如下：
![JLog](http://7xvg4t.com2.z0.glb.qiniucdn.com/JLog_test.png)

### JWidget说明
JWidget是控件的基本工具类。里面也包含有MVP V层与P层的基类，都是以base开头，主要是做V－Ｐ层公共方法的封装。另外也有一些常用控件类。如上拉加载更多，滑动返回等。详情看JWidget Library .

### JUtil说明
JUtil主要封装一些工具Library.有以下几种。
| 方法        | 说明   |
| --------   | :-----  |
| ToastUtil | Toast的相关操作|