package musicdemo.jlang.com.mimu.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import musicdemo.jlang.com.mimu.ApplicationEx;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.bean.MusicMessage;
import musicdemo.jlang.com.mimu.bean.MusicPlayInfo;
import musicdemo.jlang.com.mimu.greendao.SQLiteOpenHelperManager;
import musicdemo.jlang.com.mimu.greendao.entity.MusicPlayingInfo;
import musicdemo.jlang.com.mimu.greendao.gen.DaoSession;
import musicdemo.jlang.com.mimu.greendao.gen.MusicPlayingInfoDao;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/21.
 */

public class MusicPlayInfoManager {

    private static MusicPlayInfoManager instance;
    private Context mContext;

    private long musicDuration;
    private long musicCurrentProcess;
    private int currentPlayingIndex = -1;
    private MusicMessage musicMessage;
    //    private List<MusicInfo> musicPlayListData = new ArrayList<>();
    List<MusicPlayInfo> musicPlayListData = new ArrayList<MusicPlayInfo>();
    /**
     * 当前播放歌单集合数据
     */
    private List<MusicPlayingInfo> currentPlayingList = new ArrayList<>();
    private Map<String, MusicPlayInfo> musicSource = new HashMap<>();


    public static MusicPlayInfoManager getInstance() {
        if (instance == null) {
            instance = new MusicPlayInfoManager();
            Log.d("TAG_DATA", "getInstance: ");
        }
        return instance;
    }

    public MusicPlayInfoManager() {
        this.mContext = ApplicationEx.getInstance();
    }


    public long getMusicDuration() {
        return musicDuration;
    }

    public void setMusicDuration(long musicDuration) {
        this.musicDuration = musicDuration;
    }

    public long getMusicCurrentProcess() {
        return musicCurrentProcess;
    }

    public void setMusicCurrentProcess(long musicCurrentProcess) {
        this.musicCurrentProcess = musicCurrentProcess;
    }

    public List<MusicPlayingInfo> getCurrentPlayingList() {
        return currentPlayingList;
    }

    public List<MusicPlayInfo> getMusicPlayListData() {
        return musicPlayListData;
    }

    public int getCurrentPlayingIndex() {
        return currentPlayingIndex;
    }

    public void setCurrentPlayingIndex(int currentPlayingIndex) {
        this.currentPlayingIndex = currentPlayingIndex;
    }

    public MusicMessage getMusicMessage() {
        return musicMessage;
    }

    public void setMusicMessage(MusicMessage musicMessage) {
        this.musicMessage = musicMessage;
    }

    /**
     * 添加歌单到播放列表
     *
     * @param musicInfos Music 集合
     */
    public void addLocalPlayingList(List<MusicInfo> musicInfos, int sourceId) {
        int size = musicInfos.size();
        for (int i = 0; i < size; i++) {
            MusicInfo musicInfo = musicInfos.get(i);
            currentPlayingList.add(new MusicPlayingInfo(musicInfo.id, musicInfo.path, i, 0));
        }

        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mContext, true);
        if (daoSession != null) {
            MusicPlayingInfoDao musicPlayingListDao = daoSession.getMusicPlayingInfoDao();
            //清除上次歌单所有数据
            musicPlayingListDao.insertOrReplaceInTx(currentPlayingList);

            //保存歌单资源Id
            PreferencesUtility.getInstance(mContext).setCurrentPlayingListSourceId(sourceId);
        }
    }

    /**
     * 添加音乐到当前播放的下一曲
     *
     * @param musicInfo
     */
    public void addLocalPlayingList(MusicInfo musicInfo) {
//        if (musicInfo == null || currentPlayingInfo == null) {
//            return;
//        }
//        musicSource.put(musicInfo.path, musicInfo);
//        MusicPlayingInfo playingList = new MusicPlayingInfo(musicInfo.id, musicInfo.path
//                , currentPlayingInfo.getOrderFirst()
//                , currentPlayingInfo.getOrderSecond() + 1);
//
//        currentPlayingList.add(currentPlayingIndex, playingList);
//
//        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mApplicationEx, true);
//        if (daoSession != null) {
//            MusicPlayingListDao musicPlayingListDao = daoSession.getMusicPlayingListDao();
//            musicPlayingListDao.insertOrReplace(playingList);
//        }
    }


    /**
     * 计算当前播放Id
     *
     * @param playData 当前播放音乐数据地址
     */
    public void calcCurrentPLayingIndex(String playData) {
        int size = musicPlayListData.size();
        for (int i = 0; i < size; i++) {
            MusicPlayInfo playingList = musicPlayListData.get(i);
            if (playingList.getData().equals(playData)) {
                currentPlayingIndex = i;
                return;
            }
        }
    }

    public MusicPlayInfo getPlayInfo() {
        return musicPlayListData.get(currentPlayingIndex);
    }

    public void getPlayListDetailInfo() {
        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mContext, true);
        String strSql = "SELECT music_id,data,title,album_name,album_pic_url,artist_name,duration,order_first,order_second from" +
                "(" +
                "SELECT music_playing_info.music_id,music_playing_info.data,music_playing_info.order_first,music_playing_info.order_second," +
                "oline_music_info.title,oline_music_info.data,oline_music_info.album_name,oline_music_info.album_name,oline_music_info.album_pic_url,oline_music_info.artist_name,oline_music_info.duration " +
                "from music_playing_info LEFT JOIN oline_music_info ON music_playing_info.data=oline_music_info.data WHERE " +
                "music_playing_info.music_id = '-1' " +
                "UNION ALL " +
                "SELECT music_playing_info.music_id,music_playing_info.data,music_playing_info.order_first,music_playing_info.order_second," +
                "local_music_info.title,local_music_info.data,local_music_info.album_name,local_music_info.album_name,local_music_info.album_pic_url,local_music_info.artist_name,local_music_info.duration " +
                "from music_playing_info LEFT JOIN local_music_info ON music_playing_info.data=local_music_info.data WHERE " +
                "music_playing_info.music_id NOT IN('-1')" +
                ")T";

        Cursor cursor = daoSession.getDatabase().rawQuery(strSql, null);
        if ((cursor != null) && (cursor.moveToFirst())) {
            musicPlayListData.clear();
            do {
                long musicId = cursor.getLong(0);
                String data = cursor.getString(1);
                String title = cursor.getString(2);
                String albumName = cursor.getString(3);
                String albumPicUrl = cursor.getString(4);
                String artistName = cursor.getString(5);
                long duration = cursor.getLong(6);
                long orderFirst = cursor.getLong(7);
                long orderSecond = cursor.getLong(8);
                int type = musicId != -1 ? MusicInfo.LOCAL : MusicInfo.NET;
                MusicPlayInfo musicPlayInfo = new MusicPlayInfo(musicId, data, title, artistName, albumName, albumPicUrl, duration, type, orderFirst, orderSecond);
                musicPlayListData.add(musicPlayInfo);
                musicSource.put(data, musicPlayInfo);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
