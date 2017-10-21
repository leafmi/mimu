package musicdemo.jlang.com.musicdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.util.List;

import musicdemo.jlang.com.musicdemo.bean.MusicInfo;
import musicdemo.jlang.com.musicdemo.bean.AudioMessage;
import musicdemo.jlang.com.musicdemo.greendao.SQLiteOpenHelperManager;
import musicdemo.jlang.com.musicdemo.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/16.
 */

public class ApplicationEx extends Application {
    private static ApplicationEx instance;

    /**
     * 当前歌曲
     */
    private AudioMessage curAudioMessage;
    /**
     * 当前播放列表
     */
    private List<MusicInfo> curMusicInfos;
    /**
     * 设置当前正在播放的歌曲
     */
    private MusicInfo curMusicInfo;

    private int playingIndex = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        instance = this;
        initMusicDB();
    }

    public static ApplicationEx getInstance() {
        return instance;
    }

    public void initMusicDB() {
        SQLiteOpenHelperManager.createDaoSession(this, true);
    }



    public int getPlayingIndex() {
        return playingIndex;
    }

    public void setPlayingIndex(int playingIndex) {
        this.playingIndex = playingIndex;
    }

    public int getMusicPlayModel() {
        return PreferencesUtility.getInstance(this).getMusicPlayModel();
    }

    public void setMusicPlayModel(int playModel) {
        PreferencesUtility.getInstance(this).setMusicPlayModel(playModel);
    }

    public AudioMessage getCurAudioMessage() {
        return curAudioMessage;
    }

    public void setCurAudioMessage(final AudioMessage curAudioMessage) {
        this.curAudioMessage = curAudioMessage;
    }

    public List<MusicInfo> getCurMusicInfos() {
        return curMusicInfos;
    }

    public void setCurMusicInfos(final List<MusicInfo> curMusicInfos) {
        this.curMusicInfos = curMusicInfos;
    }

    public MusicInfo getCurMusicInfo() {
        return curMusicInfo;
    }

    public void setCurMusicInfo(MusicInfo curMusicInfo) {
        this.curMusicInfo = curMusicInfo;
    }
}
