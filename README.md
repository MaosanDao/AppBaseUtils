![版本:v0.0.1](https://img.shields.io/badge/jcenter-0.0.1-green.svg)

# AppBaseUtils
本工具类是为了更快的搭建App的初步框架而存在的，目前工具类中的功能还比较少。其中包括：
* 实现App的Activity的集中管理。
* 本地音乐的一些工具类

## 引入步骤
### 请将下方的lastVersion替换为上方的版本号
```Java
compile 'cn.vangelis:appbaseutils:$lastVersion'
```

## 内容
### Activity的管理工具
#### 在BaseApplication中进行初始化
```Java
  AppBaseUtilsManager.getAppActivityStack().init(Applicalition application);
```
#### 具体方法介绍
```Java
/**
 * 销毁所有的Activity
 */
public void removeALLActivity()

/**
* 获取栈顶的Activity
*/
public Activity getTopActivity()

/**
* 结束指定的Activity
*
* @param cls Activity
*/
public void finishActivity(Class<?> cls)

/**
* 获取当前所有的Activity集合
*/
public List<Activity> getAllActivity()

/**
* 获取指定类的Activity
*
* @param cls 类名
* @return Activity
*/
public Activity getActivity(Class<?> cls)

/**
* 销毁所有的Activity，且退出App
*/
public void exitApp()
```
### Todo-List
* 增加Log打印工具
### 更新日志
* 2018.04.02 发布版本 V1.0.0
### 说明
此框架为自己的所用，请谨慎选择。
### 联系方式
* QQ：460977141
