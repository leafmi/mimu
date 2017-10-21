package musicdemo.jlang.com.mimu.event.message;

import musicdemo.jlang.com.mimu.bean.MusicInfo;

/**
 * Created by JLang on 2017/10/21.
 */

public class EventMusicAction {
    private int action;
    private MusicInfo musicInfo;

    public EventMusicAction(int action) {
        this.action = action;
    }

    public EventMusicAction(int action, MusicInfo musicInfo) {
        this.action = action;
        this.musicInfo = musicInfo;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public MusicInfo getMusicInfo() {
        return musicInfo;
    }

    public void setMusicInfo(MusicInfo musicInfo) {
        this.musicInfo = musicInfo;
    }
}
