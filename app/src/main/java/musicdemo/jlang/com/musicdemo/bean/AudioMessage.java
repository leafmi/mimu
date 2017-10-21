package musicdemo.jlang.com.musicdemo.bean;

import java.io.Serializable;

/**
 * Created by JLang on 2017/10/18.
 */

public class AudioMessage implements Serializable {
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
    private String musicData;
    /**
     * 音乐类型
     */
    private int musicType;
    /**
     *
     */
    private String hash;


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

    public String getMusicData() {
        return musicData;
    }

    public void setMusicData(String musicData) {
        this.musicData = musicData;
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
