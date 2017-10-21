package musicdemo.jlang.com.mimu.manager;

import android.content.Context;

import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/21.
 */

public class MusicPlayerManager {
    private static MusicPlayerManager instance;

    private Context mContext;

    public static MusicPlayerManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new MusicPlayerManager(mContext);
        }
        return instance;
    }

    public MusicPlayerManager(Context mContext) {
        this.mContext = mContext;
    }

    //获取音乐播放状态
    public int getMusicPlayStatus() {
        return PreferencesUtility.getInstance(mContext).getMusicPlayStatus();
    }

    //设置音乐播放状态
    public void setMusicPlayStatus(int playStatus) {
        PreferencesUtility.getInstance(mContext).setMusicPlayStatus(playStatus);
    }
}
