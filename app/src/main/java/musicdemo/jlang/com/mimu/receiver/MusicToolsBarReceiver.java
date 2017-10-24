package musicdemo.jlang.com.mimu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import musicdemo.jlang.com.mimu.event.message.EventCancelMusicToolsBar;
import musicdemo.jlang.com.mimu.manager.MusicPlayerManager;
import musicdemo.jlang.com.mimu.util.music.MusicAction;

/**
 * 处理Music Tools Bar点击事件
 * Created by JLang on 2017/10/23.
 */

public class MusicToolsBarReceiver extends BroadcastReceiver {
    //点击播放音乐
    public static final String ACTION_TOOLS_BAR_PLAY_MUSIC = "com.lion_music_play_music";
    //继续播放
    public static final String ACTION_TOOLS_BAR_RESUME_MUSIC = "com.lion_music_resume_music";
    //点击暂停播放
    public static final String ACTION_TOOLS_BAR_PAUSE_MUSIC = "com.lion_music_pause_music ";
    //点击上一首
    public static final String ACTION_TOOLS_BAR_PRE_MUSIC = "com.lion_music_pre_music";
    //点击下一首
    public static final String ACTION_TOOLS_BAR_NEXT_MUSIC = "com.lion_music_next_music";
    //点击喜爱
    public static final String ACTION_TOOLS_BAR_FAV_MUSIC = "com.lion_music_fav_music";
    //点击Close
    public static final String ACTION_TOOLS_BAR_CLOSE = "com.lion_music_close";


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_TOOLS_BAR_PLAY_MUSIC:
                break;
            case ACTION_TOOLS_BAR_RESUME_MUSIC:
            case ACTION_TOOLS_BAR_PAUSE_MUSIC:
                MusicPlayerManager.getInstance(context).playAction();
                break;
            case ACTION_TOOLS_BAR_PRE_MUSIC:
                MusicPlayerManager.getInstance(context).playAction(MusicAction.ACTION_PRE_MUSIC, null, -1);
                break;
            case ACTION_TOOLS_BAR_NEXT_MUSIC:
                MusicPlayerManager.getInstance(context).playAction(MusicAction.ACTION_NEXT_MUSIC, null, -1);
                break;
            case ACTION_TOOLS_BAR_FAV_MUSIC:
                break;
            case ACTION_TOOLS_BAR_CLOSE:
                MusicPlayerManager.getInstance(context).playAction(MusicAction.ACTION_PAUSE_MUSIC, null, -1);
                //发送消息取消Tools bar
                EventBus.getDefault().post(new EventCancelMusicToolsBar());
                break;
        }
    }

}
