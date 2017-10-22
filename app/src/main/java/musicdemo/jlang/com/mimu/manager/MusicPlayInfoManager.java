package musicdemo.jlang.com.mimu.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import musicdemo.jlang.com.mimu.ApplicationEx;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.bean.MusicMessage;
import musicdemo.jlang.com.mimu.greendao.SQLiteOpenHelperManager;
import musicdemo.jlang.com.mimu.greendao.entity.MusicPlayingInfo;
import musicdemo.jlang.com.mimu.greendao.gen.DaoSession;
import musicdemo.jlang.com.mimu.greendao.gen.MusicPlayingInfoDao;
import musicdemo.jlang.com.mimu.greendao.gen.MusicPlayingListDao;
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
    private List<MusicInfo> musicPlayListData = new ArrayList<>();
    /**
     * 当前播放歌单集合数据
     */
    private List<MusicPlayingInfo> currentPlayingList = new ArrayList<>();
    private Map<String, MusicInfo> musicSource = new HashMap<>();


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

    public List<MusicInfo> getMusicPlayListData() {
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
        musicPlayListData.clear();
        musicPlayListData.addAll(musicInfos);

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
        int size = currentPlayingList.size();
        for (int i = 0; i < size; i++) {
            MusicPlayingInfo playingList = currentPlayingList.get(i);
            if (playingList.getMusicData().equals(playData)) {
                currentPlayingIndex = i;
                return;
            }
        }
    }


    public void getPlayListDetailInfo() {

//        "SELECT *from\n" +
//                "(\n" +
//                "SELECT music_playing_list.music_id,music_playing_list.data,oline_music_info.title from music_playing_list LEFT JOIN oline_music_info ON music_playing_list.data=oline_music_info.data WHERE\n" +
//                "music_playing_list.music_id = '-1'\n" +
//                "UNION ALL\n" +
//                "SELECT music_playing_list.music_id,music_playing_list.data,local_music_info.title from music_playing_list LEFT JOIN local_music_info ON music_playing_list.data=local_music_info.data WHERE\n" +
//                "music_playing_list.music_id NOT IN('-1')\n" +
//                ")T"

        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mContext, true);
        String str = "SELECT *from(" +
                "SELECT music_playing_list.music_id,music_playing_list.data,oline_music_info.title " +
                "from music_playing_list LEFT JOIN oline_music_info ON music_playing_list.data=oline_music_info.data WHERE music_playing_list.music_id = '-1'" +
                " UNION ALL " +
                "SELECT music_playing_list.music_id,music_playing_list.data,local_music_info.title" +
                " from music_playing_list LEFT JOIN local_music_info ON music_playing_list.data=local_music_info.data WHERE music_playing_list.music_id NOT IN('-1')" +
                ")T";
        Cursor cursor = daoSession.getDatabase().rawQuery(str, null);
    }
}
