package cn.vangelis.appbaseutils.music;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import cn.vangelis.appbaseutils.R;


/**
 * 专辑封面图片加载器
 * Created by wcy on 2015/11/27.
 */
public class CoverLoader {
    private static final String KEY_NULL = "null";
    /**
     * 缩略图缓存，用于音乐列表
     */
    private LruCache<String, Bitmap> mThumbnailCache;
    /**
     * 高斯模糊图缓存，用于播放页背景
     */
    private LruCache<String, Bitmap> mBlurCache;
    /**
     * 圆形图缓存，用于播放页CD
     */
    private LruCache<String, Bitmap> mRoundCache;

    private CoverLoader() {
        // 获取当前进程的可用内存（单位KB）
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 缓存大小为当前进程可用内存的1/8
        int cacheSize = maxMemory / 8;
        mThumbnailCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return sizeOfBitmap(bitmap);
            }
        };
        mBlurCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return sizeOfBitmap(bitmap);
            }
        };
        mRoundCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return sizeOfBitmap(bitmap);
            }
        };
    }

    /**
     * 获取bitmap内存，单位KB
     */
    private int sizeOfBitmap(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount() / 1024;
        } else {
            return bitmap.getByteCount() / 1024;
        }
    }

    public static CoverLoader getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static CoverLoader instance = new CoverLoader();
    }

    public Bitmap loadThumbnail(Context context, Music music) {
        Bitmap bitmap;
        String path = FileUtils.getAlbumFilePath(context, music);
        if (TextUtils.isEmpty(path)) {
            bitmap = mThumbnailCache.get(KEY_NULL);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_cover);
                mThumbnailCache.put(KEY_NULL, bitmap);
            }
        } else {
            bitmap = mThumbnailCache.get(path);
            if (bitmap == null) {
                bitmap = loadBitmap(path, ScreenUtils.getScreenWidth());
                if (bitmap == null) {
                    bitmap = loadThumbnail(context, null);
                }
                mThumbnailCache.put(path, bitmap);
            }
        }
        return bitmap;
    }


    /**
     * 获得指定大小的bitmap
     */
    private Bitmap loadBitmap(String path, int length) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 仅获取大小
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int maxLength = Math.max(options.outWidth, options.outHeight);
        // 压缩尺寸，避免卡顿
        int inSampleSize = maxLength / length;
        if (inSampleSize < 1) {
            inSampleSize = 1;
        }
        options.inSampleSize = inSampleSize;
        // 获取bitmap
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, options);
    }
}
