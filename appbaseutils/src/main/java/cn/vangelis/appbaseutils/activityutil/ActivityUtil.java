package cn.vangelis.appbaseutils.activityutil;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Comment: Activity管理工具
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/4/2
 * Email:Vangelis.wang@make1.cn
 */

public class ActivityUtil {

    private Application mContext;
    public static final String TAG = "AppManager";

    /**
     * 维护Activity 的list
     */
    private static Stack<Activity> mActivates = new Stack<>();

    private static class LazyHolder {
        private static final ActivityUtil INSTANCE = new ActivityUtil();
    }

    private ActivityUtil() {
    }

    public static final ActivityUtil build() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 初始化 -- 需要在基类Application中使用
     *
     * @param context Application
     */
    public void init(Application context) {
        mContext = context;
        registerActivityLifecycleCallbacks();
    }

    /**
     * 注册Activity的生命周期监听
     */
    private void registerActivityLifecycleCallbacks() {
        mContext.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                pushActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (null == mActivates || mActivates.isEmpty()) {
                    return;
                }
                if (mActivates.contains(activity)) {
                    //监听到 Activity销毁事件 将该Activity 从list中移除
                    popActivity(activity);
                }
            }
        });
    }

    /**
     * 添加Activity
     */
    private void pushActivity(Activity activity) {
        // 判断当前集合中不存在该Activity
        Log.i(TAG, "pushActivity: ac:" + activity.getLocalClassName());
        mActivates.add(activity);
    }

    /**
     * 销毁单个Activity
     */
    private void popActivity(Activity activity) {
        Log.i(TAG, "popActivity: ac:" + activity.getLocalClassName());
        mActivates.remove(activity);
    }

    /**
     * 销毁所有的Activity
     */
    public static void removeALLActivity() {
        if (mActivates == null) {
            return;
        }
        for (Activity activity : mActivates) {
            activity.finish();
        }
        mActivates.clear();
    }

    /**
     * 获取栈顶的Activity
     */
    public static Activity getTopActivity() {
        return mActivates.lastElement();
    }

    /**
     * 结束指定的Activity
     *
     * @param cls Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (cls == null) {
            return;
        }
        Iterator iterator = mActivates.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 获取当前所有的Activity集合
     */
    public static List<Activity> getAllActivity() {
        if (mActivates.size() == 0) {
            return null;
        }
        return mActivates.subList(0, mActivates.size());
    }

    /**
     * 获取指定类的Activity
     *
     * @param cls 类名
     * @return Activity
     */
    public static Activity getActivity(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        for (Activity activity1 : mActivates) {
            if (activity1.getClass().equals(cls)) {
                return activity1;
            }
        }
        return null;
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        removeALLActivity();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
