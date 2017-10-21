package musicdemo.jlang.com.mimu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import musicdemo.jlang.com.mimu.ApplicationEx;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.bean.AudioMessage;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.event.message.EventMusicAction;
import musicdemo.jlang.com.mimu.manager.MusicPlayInfoManager;
import musicdemo.jlang.com.mimu.manager.PlayingListManager;
import musicdemo.jlang.com.mimu.receiver.AudioBroadcastReceiver;
import musicdemo.jlang.com.mimu.util.music.MusicAction;
import musicdemo.jlang.com.mimu.view.PlayerSeekBar;

public class PlayDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgBg, mPlayPre, mPlayPlay, mPlayNext;
    /**
     * 播放列表Manager
     */
    private PlayingListManager playingListManager;
    private MusicInfo currentMusicInfo;
    private PlayerSeekBar mPlaySeek;
    private MusicPlayInfoManager musicPlayInfoManager;

    /**
     * 音频广播
     */
    private AudioBroadcastReceiver mAudioBroadcastReceiver;

    /**
     * 广播监听
     */
    private AudioBroadcastReceiver.AudioReceiverListener mAudioReceiverListener = new AudioBroadcastReceiver.AudioReceiverListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doAudioReceive(context, intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_detail);

        initView();
        initData();
        initService();
        registerEventBus();
    }

    private void initView() {
        mImgBg = (ImageView) findViewById(R.id.img_bg);
        mPlayPre = (ImageView) findViewById(R.id.playing_pre);
        mPlayPlay = (ImageView) findViewById(R.id.playing_play);
        mPlayNext = (ImageView) findViewById(R.id.playing_next);
        mPlaySeek = (PlayerSeekBar) findViewById(R.id.play_seek);
    }

    private void initData() {
        PlayingListManager playingListManager = PlayingListManager.getInstance();
        currentMusicInfo = playingListManager.getCurrentMusicInfo();
        musicPlayInfoManager = MusicPlayInfoManager.getInstance(this);

        mPlaySeek.setMax((int) musicPlayInfoManager.getMusicDuration());
    }


    private void listener() {
        mPlayPre.setOnClickListener(this);
        mPlayPlay.setOnClickListener(this);
        mPlayNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playing_pre:
                break;
            case R.id.playing_play:
                break;
            case R.id.playing_next:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    private void initService() {
        //注册接收音频播放广播
        mAudioBroadcastReceiver = new AudioBroadcastReceiver(getApplicationContext(), ApplicationEx.getInstance());
        mAudioBroadcastReceiver.setAudioReceiverListener(mAudioReceiverListener);
        mAudioBroadcastReceiver.registerReceiver(getApplicationContext());
    }

    /**
     * 处理音频广播事件
     *
     * @param context
     * @param intent
     */
    private void doAudioReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case AudioBroadcastReceiver.ACTION_SERVICE_PLAYING_MUSIC:
                break;
        }
    }

    private void setBackgroundDrawable(Drawable result) {
        if (result != null) {
            if (mImgBg.getDrawable() != null) {
                final TransitionDrawable td =
                        new TransitionDrawable(new Drawable[]{mImgBg.getDrawable(), result});


                mImgBg.setImageDrawable(td);
                //去除过度绘制
                td.setCrossFadeEnabled(true);
                td.startTransition(200);

            } else {
                mImgBg.setImageDrawable(result);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicActionEvent(EventMusicAction event) {
        switch (event.getAction()) {
            case MusicAction.ACTION_SERVICE_PLAY_MUSIC:

                Log.d("TAG_PROCESS_MAX", musicPlayInfoManager.getMusicDuration() + "");
                break;
            case MusicAction.ACTION_SERVICE_PLAYING_MUSIC:
                mPlaySeek.setProgress((int) musicPlayInfoManager.getMusicCurrentProcess());
                Log.d("TAG_PROCESS", musicPlayInfoManager.getMusicCurrentProcess() + "");
                break;
        }
    }
}
