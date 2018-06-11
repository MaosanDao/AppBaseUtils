package cn.vangelis.appbaseutils;

import android.app.Application;

import cn.vangelis.appbaseutils.activityutil.ActivityUtil;

/**
 * Comment: 基类
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/6/11
 * Email:Vangelis.wang@make1.cn
 */

public class AppBaseUtil {

    public static void init(Application application) {
        //Activity工具类
        ActivityUtil.build().init(application);
    }
}
