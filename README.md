![版本:v0.0.3](https://img.shields.io/badge/jCenter-0.0.3-green.svg)
![MinSDK:21](https://img.shields.io/badge/minSdk-21-orange.svg)
![Build:Passing](https://img.shields.io/teamcity/codebetter/bt428.svg)


# AppBaseUtils
本工具类是为了更快的搭建App的初步框架而存在的，目前工具类中的功能还比较少。其中包括：
* 实现App的Activity的集中管理。
* 本地音乐的一些工具类

## 引入步骤
### 请将下方的lastVersion替换为上方的版本号
```Java
compile 'cn.vangelis:appbaseutils:$lastVersion'
```
### 初始化
```Java
  AppBaseUtil.init(Applicalition application);
```
## 内容
### Activity工具类
#### 具体方法介绍
```Java
//销毁所有的Activity
ActivityUtil.removeALLActivity();
//获取栈顶的Activity
ActivityUtil.getTopActivity();
//结束指定的Activity
ActivityUtil.finishActivity(Class<?> cls);
//获取当前所有的Activity集合
ActivityUtil.getAllActivity();
//获取指定类的Activity
ActivityUtil.getActivity(Class<?> cls);
//销毁所有的Activity，且退出App
ActivityUtil.exitApp();
```
### 音乐工具类
#### 具体方法介绍
```Java
//扫描本地音乐，返回List<Music>
MusicUtils.scanLocalMusic(Context context);
//解析音乐文件中的信息
MusicUtils.getMusicInfo(Context context, String fileName, MusicInfoListener listener);
```
### 更新日志
* 2018.04.02 发布版本 V0.0.1
* 2018.06.11 发布版本 V0.0.3
### 说明
此框架为自己的所用，请谨慎选择。
### 联系方式
* QQ：460977141
