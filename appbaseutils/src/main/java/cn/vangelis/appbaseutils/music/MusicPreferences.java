package cn.vangelis.appbaseutils.music;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;


/**
 * 音乐SharedPreferences工具类
 *
 */
public class MusicPreferences {

    //音乐ID
    private static final String MUSIC_ID = "music_id";
    //播放模式
    private static final String PLAY_MODE = "play_mode";
    //当前的播放音乐的序列
    private static final String MUSIC_INDEX = "music_index";

    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    //获取当前音乐的ID
    public static long getCurrentSongId() {
        return getLong(MUSIC_ID, -1);
    }

    //存储当前音乐的ID
    public static void saveCurrentSongId(long id) {
        saveLong(MUSIC_ID, id);
    }

    public static int getCurrentMusicIndex(){
        return getInt(MUSIC_INDEX, -1);
    }

    public static void saveCurrentMusicIndex(int index){
        saveInt(MUSIC_INDEX, index);
    }

    //获取当前的播放模式
    public static int getPlayMode() {
        return getInt(PLAY_MODE, 0);
    }

    //存储当前的播放模式
    public static void savePlayMode(int mode) {
        saveInt(PLAY_MODE, mode);
    }


    private static boolean getBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    private static void saveBoolean(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    private static int getInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    private static void saveInt(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    private static long getLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    private static void saveLong(String key, long value) {
        getPreferences().edit().putLong(key, value).apply();
    }

    private static String getString(String key, @Nullable String defValue) {
        return getPreferences().getString(key, defValue);
    }

    private static void saveString(String key, @Nullable String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(sContext);
    }
}
