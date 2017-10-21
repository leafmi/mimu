package musicdemo.jlang.com.mimu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Date;

import musicdemo.jlang.com.mimu.ApplicationEx;

/**
 * Created by JLang on 2017/10/18.
 */

public class AudioBroadcastReceiver {

    private Context mContext;
    private ApplicationEx mApplicationEx;
    /**
     * 是否注册成功
     */
    private boolean isRegisterSuccess = false;
    /**
     * 注册成功广播
     */
    private String ACTION_MUSIC_SUCCESS = "com.zlm.hp.music.success_" + new Date().getTime();

    //空音乐
    public static final String ACTION_NULL_MUSIC = "com.zlm.hp.null.music";
    //初始化音乐
    public static final String ACTION_INIT_MUSIC = "com.zlm.hp.init.music";
    //点击播放音乐
    public static final String ACTION_PLAY_MUSIC = "com.zlm.hp.play.music";
    //继续播放
    public static final String ACTION_RESUME_MUSIC = "com.zlm.hp.resume.music";
    //点击暂停播放
    public static final String ACTION_PAUSE_MUSIC = "com.zlm.hp.pause.music";
    //点击音乐快进
    public static final String ACTION_SEEKTO_MUSIC = "com.zlm.hp.seekto.music";
    //点击上一首
    public static final String ACTION_PRE_MUSIC = "com.zlm.hp.pre.music";
    //点击下一首
    public static final String ACTION_NEXT_MUSIC = "com.zlm.hp.next.music";


    //播放器开始播放
    public static final String ACTION_SERVICE_PLAY_MUSIC = "com.zlm.hp.service.play.music";
    //播放器暂停
    public static final String ACTION_SERVICE_PAUSE_MUSIC = "com.zlm.hp.service.pause.music";
    //播放器唤醒
    public static final String ACTION_SERVICE_RESUME_MUSIC = "com.zlm.hp.service.resume.music";
    //播放器播放中
    public static final String ACTION_SERVICE_PLAYING_MUSIC = "com.zlm.hp.service.playing.music";
    //播放错误
    public static final String ACTION_SERVICE_PLAYERROR_MUSIC = "com.zlm.hp.service.playerror.music";


    //歌手图片加载
    public static final String ACTION_SINGERPIC_LOADED = "com.zlm.hp.singerpic.loaded";

    private AudioReceiverListener mAudioReceiverListener;
    private BroadcastReceiver mAudioBroadcastReceiver;
    private IntentFilter mAudioIntentFilter;

    public AudioBroadcastReceiver(Context mContext, ApplicationEx mApplicationEx) {
        this.mContext = mContext;
        this.mApplicationEx = mApplicationEx;

        mAudioIntentFilter = new IntentFilter();
        mAudioIntentFilter.addAction(ACTION_NULL_MUSIC);
        mAudioIntentFilter.addAction(ACTION_INIT_MUSIC);
        mAudioIntentFilter.addAction(ACTION_PLAY_MUSIC);
        mAudioIntentFilter.addAction(ACTION_RESUME_MUSIC);
        mAudioIntentFilter.addAction(ACTION_PAUSE_MUSIC);
        mAudioIntentFilter.addAction(ACTION_SEEKTO_MUSIC);
        mAudioIntentFilter.addAction(ACTION_PRE_MUSIC);
        mAudioIntentFilter.addAction(ACTION_NEXT_MUSIC);

        mAudioIntentFilter.addAction(ACTION_SERVICE_PLAY_MUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_PAUSE_MUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_RESUME_MUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_PLAYING_MUSIC);
        mAudioIntentFilter.addAction(ACTION_SERVICE_PLAYERROR_MUSIC);

        mAudioIntentFilter.addAction(ACTION_SINGERPIC_LOADED);
    }


    /**
     * 注册广播
     *
     * @param context
     */
    public void registerReceiver(Context context) {
        if (mAudioBroadcastReceiver == null) {
            //
            mAudioBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
//                    Message msg = new Message();
//                    msg.obj = intent;
//                    mAudioHandler.sendMessage(msg);
                    mAudioReceiverListener.onReceive(mContext, intent);
                }
            };
            mContext.registerReceiver(mAudioBroadcastReceiver, mAudioIntentFilter);
            //发送注册成功广播
            Intent successIntent = new Intent(ACTION_MUSIC_SUCCESS);
            successIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            mContext.sendBroadcast(successIntent);

        }
    }

    /**
     * 取消注册广播
     */
    public void unregisterReceiver(Context context) {
        if (mAudioBroadcastReceiver != null && isRegisterSuccess) {
            mContext.unregisterReceiver(mAudioBroadcastReceiver);
        }
    }

    public void setAudioReceiverListener(AudioReceiverListener audioReceiverListener) {
        this.mAudioReceiverListener = audioReceiverListener;
    }

    ///////////////////////////////////
    public interface AudioReceiverListener {
        void onReceive(Context context, Intent intent);
    }
}
