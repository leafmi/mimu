package musicdemo.jlang.com.mimu.util.music;

/**
 * Created by JLang on 2017/10/21.
 */

public class MusicAction {
    //空音乐
    public static final int ACTION_NULL_MUSIC = 0;
    //初始化音乐
    public static final int ACTION_INIT_MUSIC = 1;
    //点击播放音乐
    public static final int ACTION_PLAY_MUSIC = 2;
    //继续播放
    public static final int ACTION_RESUME_MUSIC = 3;
    //点击暂停播放
    public static final int ACTION_PAUSE_MUSIC = 4;
    //点击音乐快进
    public static final int ACTION_SEEKTO_MUSIC = 5;
    //点击上一首
    public static final int ACTION_PRE_MUSIC = 6;
    //点击下一首
    public static final int ACTION_NEXT_MUSIC = 7;


    //播放器开始播放
    public static final int ACTION_SERVICE_PLAY_MUSIC = 8;
    //播放器暂停
    public static final int ACTION_SERVICE_PAUSE_MUSIC = 9;
    //播放器唤醒
    public static final int ACTION_SERVICE_RESUME_MUSIC = 10;
    //播放器播放中
    public static final int ACTION_SERVICE_PLAYING_MUSIC = 11;
    //播放错误
    public static final int ACTION_SERVICE_PLAYERROR_MUSIC = 12;

}
