package musicdemo.jlang.com.musicdemo.manager;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import musicdemo.jlang.com.musicdemo.ApplicationEx;
import musicdemo.jlang.com.musicdemo.bean.AudioMessage;
import musicdemo.jlang.com.musicdemo.bean.MusicInfo;
import musicdemo.jlang.com.musicdemo.greendao.SQLiteOpenHelperManager;
import musicdemo.jlang.com.musicdemo.greendao.entity.MusicPlayingList;
import musicdemo.jlang.com.musicdemo.greendao.gen.DaoSession;
import musicdemo.jlang.com.musicdemo.greendao.gen.MusicPlayingListDao;
import musicdemo.jlang.com.musicdemo.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/20.
 */

public class PlayingListManager {
    private static PlayingListManager instance;
    private ApplicationEx mApplicationEx;

    /**
     * 当前播放歌单集合数据
     */
    private List<MusicPlayingList> currentPlayingList = new ArrayList<>();
    private int currentPlayingIndex = -1;
    private MusicPlayingList currentPlayingInfo;

    /**
     * 当前歌曲
     */
    private AudioMessage curAudioMessage;


    public static PlayingListManager getInstance() {
        if (instance == null) {
            synchronized (PlayingListManager.class) {
                instance = new PlayingListManager(ApplicationEx.getInstance());
            }
        }
        return instance;
    }

    private PlayingListManager(ApplicationEx mApplicationEx) {
        //初始化数据
        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mApplicationEx, true);
        List<MusicPlayingList> list = daoSession.getMusicPlayingListDao().queryBuilder()
                .orderAsc(MusicPlayingListDao.Properties.OrderFirst)
                .orderAsc(MusicPlayingListDao.Properties.OrderSecond).build().list();
        currentPlayingList.addAll(list);


        this.mApplicationEx = mApplicationEx;
    }

    public List<MusicPlayingList> getCurrentPlayingList() {
        return currentPlayingList;
    }

    public void setCurrentPlayingList(List<MusicPlayingList> currentPlayingList) {
        this.currentPlayingList = currentPlayingList;
    }

    /**
     * 获取当前播放歌单的索引
     *
     * @return 当前播放索引
     */
    public int getCurrentPlayingIndex() {
        return currentPlayingIndex;
    }

    /**
     * 设置当前播放歌单的索引
     *
     * @param currentPlayingIndex 当前播放索引
     */
    public void setCurrentPlayingIndex(int currentPlayingIndex) {
        this.currentPlayingIndex = currentPlayingIndex;
    }

    public MusicPlayingList getCurrentPlayingInfo() {
        if (currentPlayingInfo == null) {
            if (currentPlayingIndex != -1 && currentPlayingIndex < currentPlayingList.size())
                currentPlayingInfo = currentPlayingList.get(currentPlayingIndex);
        }
        return currentPlayingInfo;
    }

    public AudioMessage getCurAudioMessage() {
        return curAudioMessage;
    }

    public void setCurAudioMessage(AudioMessage curAudioMessage) {
        this.curAudioMessage = curAudioMessage;
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
            currentPlayingList.add(new MusicPlayingList(musicInfo.id, musicInfo.path, i, 0));
        }
        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mApplicationEx, true);
        if (daoSession != null) {
            MusicPlayingListDao musicPlayingListDao = daoSession.getMusicPlayingListDao();
            //清除上次歌单所有数据
            musicPlayingListDao.insertOrReplaceInTx(currentPlayingList);

            //保存歌单资源Id
            PreferencesUtility.getInstance(mApplicationEx).setCurrentPlayingListSourceId(sourceId);
        }
    }

    /**
     * 添加音乐到当前播放的下一曲
     *
     * @param musicInfo
     */
    public void addLocalPlayingList(MusicInfo musicInfo) {
        if (musicInfo == null || currentPlayingInfo == null) {
            return;
        }
        MusicPlayingList playingList = new MusicPlayingList(musicInfo.id, musicInfo.path
                , currentPlayingInfo.getOrderFirst()
                , currentPlayingInfo.getOrderSecond() + 1);

        currentPlayingList.add(currentPlayingIndex, playingList);

        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mApplicationEx, true);
        if (daoSession != null) {
            MusicPlayingListDao musicPlayingListDao = daoSession.getMusicPlayingListDao();
            musicPlayingListDao.insertOrReplace(playingList);
        }
    }


    /**
     * 计算当前播放Id
     *
     * @param playData 当前播放音乐数据地址
     */
    public void calcCurrentPLayingIndex(String playData) {
        int size = currentPlayingList.size();
        for (int i = 0; i < size; i++) {
            MusicPlayingList playingList = currentPlayingList.get(i);
            if (playingList.getMusicData().equals(playData)) {
                currentPlayingIndex = i;
                return;
            }
        }
    }
}
