package musicdemo.jlang.com.mimu.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import musicdemo.jlang.com.mimu.bean.AudioMessage;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.event.message.EventMusicAction;
import musicdemo.jlang.com.mimu.manager.AudioPlayerManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayInfoManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayerManager;
import musicdemo.jlang.com.mimu.receiver.AudioBroadcastReceiver;
import musicdemo.jlang.com.mimu.util.ToastUtil;
import musicdemo.jlang.com.mimu.util.music.MusicAction;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by JLang on 2017/10/21.
 */

public class MusicPlayerService extends Service {

    private MusicPlayerManager musicPlayerManager;
    private MusicPlayInfoManager musicPlayInfoManager;
    /**
     * 播放器
     */
    private IjkMediaPlayer mMediaPlayer;
    private boolean isSeekTo;

    /**
     * 播放线程
     */
    private Thread mPlayerThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        musicPlayerManager = MusicPlayerManager.getInstance(this);
        musicPlayInfoManager = MusicPlayInfoManager.getInstance(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 发送music 服务action
     *
     * @param action action
     */
    private void postMusicServiceAction(int action) {
        EventBus.getDefault().post(new EventMusicAction(action));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicActionEvent(EventMusicAction event) {
        switch (event.getAction()) {
            case MusicAction.ACTION_NULL_MUSIC:
                break;
            case MusicAction.ACTION_INIT_MUSIC:
                break;
            case MusicAction.ACTION_PLAY_MUSIC:
                playMusic(event.getMusicInfo());
                break;
            case MusicAction.ACTION_RESUME_MUSIC:
                resumeMusic();
                break;
            case MusicAction.ACTION_PAUSE_MUSIC:
                pauseMusic();
                break;
            case MusicAction.ACTION_SEEKTO_MUSIC:
                seekToMusic();
                break;
            case MusicAction.ACTION_PRE_MUSIC:
                preMusic();
                break;
            case MusicAction.ACTION_NEXT_MUSIC:
                nextMusic();
                break;
        }
    }

    /**
     * 播放音乐
     */
    private void playMusic(MusicInfo musicInfo) {
        releasePlayer();
        //开始初始化音乐
        postMusicServiceAction(MusicAction.ACTION_INIT_MUSIC);

        if (musicInfo.getType() == MusicInfo.LOCAL) {
            //播放本地音乐
            playLocalMusic(musicInfo);
        } else {
            //播放网络音乐
        }
    }


    private void playLocalMusic(MusicInfo musicInfo) {
        try {
            mMediaPlayer = new IjkMediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(musicInfo.path);
            mMediaPlayer.prepareAsync();

            //设置当播放的状态
            musicPlayerManager.setMusicPlayStatus(AudioPlayerManager.PLAYING);

            mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(IMediaPlayer mp) {
                    mMediaPlayer.start();
                    isSeekTo = false;
                }
            });
            mMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer mp) {
                    //播放完成，执行下一首操作
                    nextMusic();
                }
            });
            mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(IMediaPlayer mp, int what, int extra) {
                    playError();
                    return false;
                }
            });
            //音乐初始化完成回调
            mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
//                    if (playingListManager.getCurAudioMessage() != null) {
//                        AudioMessage audioMessage = playingListManager.getCurAudioMessage();
//                        if (audioMessage.getPlayProgress() > 0) {
//                            isSeekTo = true;
//                            mMediaPlayer.seekTo(audioMessage.getPlayProgress());
//                        } else {
//                            mMediaPlayer.start();
//                        }
//
//                    }
                    //获取歌曲的总时常
                    musicPlayInfoManager.setMusicDuration(mMediaPlayer.getDuration());
                    mMediaPlayer.start();
                    //设置当播放的状态
                    musicPlayerManager.setMusicPlayStatus(AudioPlayerManager.PLAYING);
                    //发送服播放音乐消息
//                    postMusicServiceAction(MusicAction.ACTION_SERVICE_PLAY_MUSIC);
                    EventBus.getDefault().postSticky(new EventMusicAction(MusicAction.ACTION_SERVICE_PLAY_MUSIC));
                }
            });

            if (mPlayerThread == null) {
                mPlayerThread = new Thread(new MusicPlayerService.PlayerRunAble());
                mPlayerThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            playError();
        }
    }

    /**
     * 播放出错，发送消息，并在一秒后自动播放下一首
     */
    private void playError() {
        //发送播放错误广播
        EventBus.getDefault().post(new EventMusicAction(MusicAction.ACTION_SERVICE_PLAYERROR_MUSIC));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //播放下一首
                nextMusic();
            }
        }, 1000);
    }


    private void playOnlineMusic() {

    }


    /**
     * 唤醒播放音乐
     */
    private void resumeMusic() {

    }

    /**
     * 暂停音乐
     */
    private void pauseMusic() {

    }

    /**
     * 快进
     */
    private void seekToMusic() {

    }

    /**
     * 上一曲
     */
    private void preMusic() {

    }

    /**
     * 下一曲
     */
    private void nextMusic() {

    }

    /**
     * 播放线程
     */
    private class PlayerRunAble implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                        Thread.sleep(100);//方便后面用来刷新歌词
                        musicPlayInfoManager.setMusicCurrentProcess(mMediaPlayer.getCurrentPosition());
                        //发送正在播放消息
                        postMusicServiceAction(MusicAction.ACTION_SERVICE_PLAYING_MUSIC);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

        /**
         * 释放播放器
         */
        private void releasePlayer() {
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            System.gc();
        }
    }
