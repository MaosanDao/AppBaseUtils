package cn.vangelis.appbaseutils.music;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.vangelis.appbaseutils.R;


/**
 * 文件工具类
 * Created by wcy on 2016/1/3.
 */
public class FileUtils {
    private static final String MP3 = ".mp3";
    private static final String LRC = ".lrc";

    private static String getAppDir() {
        return Environment.getExternalStorageDirectory() + "/PonyMusic";
    }

    public static String getMusicDir() {
        String dir = getAppDir() + "/Music/";
        return mkdirs(dir);
    }

    public static String getLrcDir() {
        String dir = getAppDir() + "/Lyric/";
        return mkdirs(dir);
    }

    public static String getAlbumDir() {
        String dir = getAppDir() + "/Album/";
        return mkdirs(dir);
    }

    public static String getLogDir() {
        String dir = getAppDir() + "/Log/";
        return mkdirs(dir);
    }

    public static String getSplashDir(Context context) {
        String dir = context.getFilesDir() + "/splash/";
        return mkdirs(dir);
    }

    public static String getRelativeMusicDir() {
        String dir = "PonyMusic/Music/";
        return mkdirs(dir);
    }

    /**
     * 获取封面图片路径<br>
     *
     * @return 如果存在返回路径，否则返回null
     */
    public static String getAlbumFilePath(Context context, Music music) {
        if (music == null) {
            return null;
        }

        String albumFilePath = music.getCoverPath();
        if (TextUtils.isEmpty(albumFilePath) || !exists(albumFilePath)) {
            albumFilePath = getAlbumDir() + getAlbumFileName(context,music.getArtist(), music.getTitle());
            if (!exists(albumFilePath)) {
                albumFilePath = null;
            }
        }
        return albumFilePath;
    }

    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    private static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static String getMp3FileName(Context context, String artist, String title) {
        return getFileName(context,artist, title) + MP3;
    }


    public static String getAlbumFileName(Context context, String artist, String title) {
        return getFileName(context,artist, title);
    }

    public static String getFileName(Context context, String artist, String title) {
        artist = stringFilter(artist);
        title = stringFilter(title);
        if (TextUtils.isEmpty(artist)) {
            artist = context.getString(R.string.unknown);
        }
        if (TextUtils.isEmpty(title)) {
            title = context.getString(R.string.unknown);
        }
        return artist + " - " + title;
    }

    public static String getArtistAndAlbum(String artist, String album) {
        if (TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return "";
        } else if (!TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return artist;
        } else if (TextUtils.isEmpty(artist) && !TextUtils.isEmpty(album)) {
            return album;
        } else {
            return artist + " - " + album;
        }
    }

    /**
     * 过滤特殊字符(\/:*?"<>|)
     */
    private static String stringFilter(String str) {
        if (str == null) {
            return null;
        }
        String regEx = "[\\/:*?\"<>|]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static float b2mb(int b) {
        String mb = String.format(Locale.getDefault(), "%.2f", (float) b / 1024 / 1024);
        return Float.valueOf(mb);
    }
}