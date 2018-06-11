package cn.vangelis.appbaseutils.music;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.vangelis.appbaseutils.R;

/**
 * Comment: 歌曲工具类
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/6/11
 * Email:Vangelis.wang@make1.cn
 */
public class MusicUtils {

    /**
     * 扫描本地歌曲
     */
    public static List<Music> scanLocalMusic(Context context) {
        List<Music> musics = new ArrayList<>();
        musics.clear();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return null;
        }

        int i = 0;
        while (cursor.moveToNext()) {
            // 是否为音乐，魅族手机上始终为0
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if (!SystemUtils.isFlyme() && isMusic == 0) {
                continue;
            }

            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String unknown = context.getString(R.string.unknown);
            artist = (TextUtils.isEmpty(artist) || artist.toLowerCase().contains("unknown")) ? unknown : artist;
            String album = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            String coverPath = getCoverPath(context, albumId);
            String fileName = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
            long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            Music music = new Music();
            music.setId(id);
            music.setType(Music.Type.LOCAL);
            music.setTitle(title);
            music.setArtist(artist);
            music.setAlbum(album);
            music.setDuration(duration);
            music.setPath(path);
            music.setCoverPath(coverPath);
            music.setFileName(fileName);
            music.setFileSize(fileSize);
            if (++i <= 20) {
                // 只加载前20首的缩略图
                CoverLoader.getInstance().loadThumbnail(context, music);
            }
            musics.add(music);
        }
        cursor.close();

        return musics;
    }

    /**
     * 获取专辑图片的地址
     */
    private static String getCoverPath(Context context, long albumId) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://media/external/audio/albums/" + albumId),
                new String[]{"album_art"}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext() && cursor.getColumnCount() > 0) {
                path = cursor.getString(0);
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 获取歌曲中的具体信息
     */
    public static void getMusicInfo(Context context, String fileName, MusicInfoListener listener) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            AssetFileDescriptor mAfd = context.getAssets().openFd(fileName);
            mmr.setDataSource(mAfd.getFileDescriptor(), mAfd.getStartOffset(), mAfd.getLength());
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            // 播放时长单位为毫秒
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            // 图片，可以通过BitmapFactory.decodeByteArray转换为bitmap图片
            byte[] pic = mmr.getEmbeddedPicture();
            if (listener != null) {
                listener.success(title, album, artist, Long.valueOf(duration), new String(pic));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.exception(e);
            }
        }
    }


    public interface MusicInfoListener {

        /**
         * 获取成功
         *
         * @param title    标题
         * @param album    专辑名
         * @param artist   歌手
         * @param duration 时长（毫秒）
         * @param pic      专辑图片
         */
        void success(String title, String album, String artist, long duration, String pic);

        /**
         * 获取出错
         */
        void exception(Exception e);
    }


}
