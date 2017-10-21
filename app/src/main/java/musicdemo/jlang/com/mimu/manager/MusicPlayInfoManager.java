package musicdemo.jlang.com.mimu.manager;

import android.content.Context;

/**
 * Created by JLang on 2017/10/21.
 */

public class MusicPlayInfoManager {

    private static MusicPlayInfoManager instance;
    private Context mContext;

    private long musicDuration;
    private long musicCurrentProcess;


    public static MusicPlayInfoManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new MusicPlayInfoManager(mContext);
        }
        return instance;
    }

    public MusicPlayInfoManager(Context mContext) {
        this.mContext = mContext;
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
}
