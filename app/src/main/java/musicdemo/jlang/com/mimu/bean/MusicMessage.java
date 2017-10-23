package musicdemo.jlang.com.mimu.bean;

import java.io.Serializable;

/**
 * Created by JLang on 2017/10/18.
 */

public class MusicMessage implements Serializable {
    public static final String KEY = "com.zlm.hp.am.key";
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 播放进度
     */
    private long playProgress;
    /**
     * 音频信息
     */
    private MusicPlayInfo musicInfo;
    /**
     * 音乐类型
     */
    private int musicType;
    /**
     *
     */
    private String hash;

    public MusicMessage(MusicPlayInfo musicInfo, int musicType) {
        this.musicInfo = musicInfo;
        this.musicType = musicType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public long getPlayProgress() {
        return playProgress;
    }

    public void setPlayProgress(long playProgress) {
        this.playProgress = playProgress;
    }

    public MusicPlayInfo getMusicInfo() {
        return musicInfo;
    }

    public void setMusicInfo(MusicPlayInfo musicInfo) {
        this.musicInfo = musicInfo;
    }

    public int getMusicType() {
        return musicType;
    }

    public void setMusicType(int musicType) {
        this.musicType = musicType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
