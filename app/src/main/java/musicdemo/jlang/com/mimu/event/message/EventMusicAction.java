package musicdemo.jlang.com.mimu.event.message;

import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.bean.MusicMessage;

/**
 * Created by JLang on 2017/10/21.
 */

public class EventMusicAction {
    private int action;
    private String MusicInfoPath;
    private MusicMessage musicMessage;

    public EventMusicAction(int action) {
        this.action = action;
    }

    public EventMusicAction(int action, String musicInfoPath) {
        this.action = action;
        MusicInfoPath = musicInfoPath;
    }

    public EventMusicAction(int action, MusicMessage musicMessage) {
        this.action = action;
        this.musicMessage = musicMessage;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getMusicInfoPath() {
        return MusicInfoPath;
    }

    public void setMusicInfoPath(String musicInfoPath) {
        MusicInfoPath = musicInfoPath;
    }

    public MusicMessage getMusicMessage() {
        return musicMessage;
    }
}
