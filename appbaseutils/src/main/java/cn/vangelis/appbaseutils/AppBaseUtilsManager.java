package cn.vangelis.appbaseutils;

/**
 * Comment: 基础管理类
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/4/2
 * Email:Vangelis.wang@make1.cn
 */

public class AppBaseUtilsManager {

    /**
     * 获取Activity栈工具类
     * @return AppActivityStackUtil
     */
    public static AppActivityStackUtil getAppActivityStack(){
        return AppActivityStackUtil.getInstance();
    }
}
