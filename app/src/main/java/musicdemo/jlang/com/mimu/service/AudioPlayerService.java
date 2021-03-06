//package musicdemo.jlang.com.mimu.service;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.media.AudioManager;
//import android.os.Handler;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import musicdemo.jlang.com.mimu.ApplicationEx;
//import musicdemo.jlang.com.mimu.bean.MusicInfo;
//import musicdemo.jlang.com.mimu.bean.MusicMessage;
//import musicdemo.jlang.com.mimu.greendao.entity.MusicPlayingInfo;
//import musicdemo.jlang.com.mimu.manager.AudioPlayerManager;
//import musicdemo.jlang.com.mimu.manager.PlayingListManager;
//import musicdemo.jlang.com.mimu.receiver.AudioBroadcastReceiver;
//import musicdemo.jlang.com.mimu.util.ToastUtil;
//import tv.danmaku.ijk.media.player.IMediaPlayer;
//import tv.danmaku.ijk.media.player.IjkMediaPlayer;
//
///**
// * Created by JLang on 2017/10/18.
// */
//
//public class AudioPlayerService extends Service {
//
//    private ApplicationEx mApplicationEx;
//    private PlayingListManager playingListManager;
//    private AudioPlayerManager audioPlayerManager;
//    /**
//     * 播放器
//     */
//    private IjkMediaPlayer mMediaPlayer;
//    /**
//     * 播放线程
//     */
//    private Thread mPlayerThread = null;
//    /**
//     * 音频广播
//     */
//    private AudioBroadcastReceiver mAudioBroadcastReceiver;
//    /**
//     * 是否正在快进
//     */
//    private boolean isSeekTo = false;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mApplicationEx = (ApplicationEx) getApplication();
//        playingListManager = PlayingListManager.getInstance();
//        audioPlayerManager = AudioPlayerManager.getInstance(this, mApplicationEx);
//        //注册接收音频播放广播
//        mAudioBroadcastReceiver = new AudioBroadcastReceiver(getApplicationContext(), mApplicationEx);
//        mAudioBroadcastReceiver.setAudioReceiverListener(mAudioReceiverListener);
//        mAudioBroadcastReceiver.registerReceiver(getApplicationContext());
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mAudioBroadcastReceiver.unregisterReceiver(getApplicationContext());
//    }
//
//    /**
//     * 广播监听
//     */
//    private AudioBroadcastReceiver.AudioReceiverListener mAudioReceiverListener = new AudioBroadcastReceiver.AudioReceiverListener() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            doAudioReceive(context, intent);
//        }
//    };
//
//    /**
//     * 广播处理
//     *
//     * @param context
//     * @param intent
//     */
//    private void doAudioReceive(Context context, Intent intent) {
//        String action = intent.getAction();
//        switch (action) {
//            case AudioBroadcastReceiver.ACTION_NULL_MUSIC:
//                //释放数据
//                releasePlayer();
//                resetPlayDataProgress();
//                break;
//            case AudioBroadcastReceiver.ACTION_PLAY_MUSIC:
//                //播放歌曲
//                playMusic((MusicMessage) intent.getSerializableExtra(MusicMessage.KEY), false);
//                break;
//            case AudioBroadcastReceiver.ACTION_PAUSE_MUSIC:
//                //暂停歌曲
//                pauseMusic();
//                break;
//            case AudioBroadcastReceiver.ACTION_RESUME_MUSIC:
//                //唤醒歌曲
//                resumeMusic((MusicMessage) intent.getSerializableExtra(MusicMessage.KEY));
//                break;
//            case AudioBroadcastReceiver.ACTION_SEEKTO_MUSIC:
//                //歌曲快进
//                seekToMusic((MusicMessage) intent.getSerializableExtra(MusicMessage.KEY));
//                break;
//            case AudioBroadcastReceiver.ACTION_NEXT_MUSIC:
//                //下一首
//                nextMusic();
//                break;
//            case AudioBroadcastReceiver.ACTION_PRE_MUSIC:
//                //上一首
//                preMusic();
//                break;
//        }
//
////        if (action.equals(AudioBroadcastReceiver.ACTION_NULL_MUSIC)
////                || action.equals(AudioBroadcastReceiver.ACTION_INIT_MUSIC)
////                || action.equals(AudioBroadcastReceiver.ACTION_SINGERPIC_LOADED)
////                || action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PLAY_MUSIC)
////                || action.equals(AudioBroadcastReceiver.ACTION_SERVICE_RESUME_MUSIC)
////                || action.equals(AudioBroadcastReceiver.ACTION_SERVICE_PAUSE_MUSIC)) {
////
////            //处理通知栏数据
////            Message msg = new Message();
////            msg.obj = intent;
////            msg.what = 0;
////            mNotificationHandler.sendMessage(msg);
////        }
//    }
//
//
//    /**
//     * 上一首
//     */
//    private void preMusic() {
//        int playModel = mApplicationEx.getMusicPlayModel();
//        MusicInfo musicInfo = AudioPlayerManager.getInstance(getApplicationContext(), mApplicationEx).preMusic(playModel);
//        if (musicInfo == null) {
//            releasePlayer();
//            resetPlayDataProgress();
//
//            //发送空数据广播
//            Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULL_MUSIC);
//            nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//            sendBroadcast(nullIntent);
//
//            return;
//        }
//
//        MusicMessage musicMessage = new MusicMessage();
//        musicMessage.getMusicData();
//        playMusic(musicMessage, true);
//    }
//
//    /**
//     * 下一首
//     */
//    private void nextMusic() {
//        int playModel = mApplicationEx.getMusicPlayModel();
//        MusicPlayingInfo playingList = AudioPlayerManager.getInstance(getApplicationContext(), mApplicationEx).nextMusic(playModel);
//        if (playingList == null) {
//            releasePlayer();
//            resetPlayDataProgress();
//
//            //发送空数据广播
//            Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULL_MUSIC);
//            nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//            sendBroadcast(nullIntent);
//            return;
//        }
//        MusicMessage musicMessage = new MusicMessage();
//        musicMessage.setMusicData(playingList.getMusicData());
//        musicMessage.setMusicType(playingList.getMusicId() != -1 ? MusicInfo.LOCAL : MusicInfo.NET);
//        playMusic(musicMessage, true);
//    }
//
//    /**
//     * 快进
//     *
//     * @param musicMessage
//     */
//    private void seekToMusic(MusicMessage musicMessage) {
//        if (mMediaPlayer != null) {
//            isSeekTo = true;
//            mMediaPlayer.seekTo(musicMessage.getPlayProgress());
//        }
//    }
//
//    /**
//     * 唤醒播放
//     */
//    private void resumeMusic(MusicMessage musicMessage) {
//        if (mMediaPlayer != null) {
//            isSeekTo = true;
//            mMediaPlayer.seekTo(musicMessage.getPlayProgress());
//        }
//        audioPlayerManager.setMusicPlayStatus(AudioPlayerManager.PLAYING);
//
//        Intent nextIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_RESUME_MUSIC);
//        nextIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        sendBroadcast(nextIntent);
//    }
//
//    /**
//     * 暂停播放
//     */
//    private void pauseMusic() {
//        if (mMediaPlayer != null) {
//            if (mMediaPlayer.isPlaying()) {
//                mMediaPlayer.pause();
//            }
//        }
//        audioPlayerManager.setMusicPlayStatus(AudioPlayerManager.PAUSE);
//        Intent nextIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PAUSE_MUSIC);
//        nextIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        sendBroadcast(nextIntent);
//    }
//
//    /**
//     * 播放歌曲
//     *
//     * @param musicMessage
//     */
//    private void playMusic(MusicMessage musicMessage, boolean isNextOrPre) {
//        //释放数据
//        releasePlayer();
//        if (musicMessage == null) {
//            return;
//        }
//        //设置当前播放数据
//        playingListManager.setCurMusicMessage(musicMessage);
//        Log.d("TAG_MUSIC_MESAGE", musicMessage.getMusicData());
//        //发送init的广播
//        Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INIT_MUSIC);
//        initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        sendBroadcast(initIntent);
//
//        if (musicMessage.getMusicType() == MusicInfo.LOCAL) {
//            //播放本地歌曲
//            playLocalMusic(musicMessage);
//        } else {
//            //播放网络歌曲
////            doNetMusic();
//        }
//
//
////        if (mApplicationEx.getCurMusicInfo() != null) {
////            if (isNextOrPre) {
////                //设置当前播放数据
////                playingListManager.setCurMusicMessage(musicMessage);
////                //发送init的广播
////                Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INIT_MUSIC);
////                //initIntent.putExtra(MusicMessage.KEY, musicMessage);
////                initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
////                sendBroadcast(initIntent);
////            }
////        } else {
////            //设置当前播放数据
////            playingListManager.setCurMusicMessage(musicMessage);
////            //发送init的广播
////            Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INIT_MUSIC);
////            //initIntent.putExtra(MusicMessage.KEY, musicMessage);
////            initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
////            sendBroadcast(initIntent);
////        }
////        if (musicInfo.getMusicId() != -1) {
////            //播放本地歌曲
////            playLocalMusic(musicMessage);
////        } else {
////            //播放网络歌曲
//////            doNetMusic();
////        }
//    }
//
//    /**
//     * 播放本地歌曲
//     *
//     * @param musicMessage
//     */
//    private void playLocalMusic(MusicMessage musicMessage) {
//        try {
//            mMediaPlayer = new IjkMediaPlayer();
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mMediaPlayer.setDataSource(musicMessage.getMusicData());
//            mMediaPlayer.prepareAsync();
//
//            //设置当播放的状态
//            audioPlayerManager.setMusicPlayStatus(AudioPlayerManager.PLAYING);
//
//            mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
//                @Override
//                public void onSeekComplete(IMediaPlayer mp) {
//                    mMediaPlayer.start();
//                    isSeekTo = false;
//                }
//            });
//            mMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(IMediaPlayer mp) {
//                    //播放完成，执行下一首操作
//                    nextMusic();
//                }
//            });
//            mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(IMediaPlayer mp, int what, int extra) {
//                    playError();
//                    return false;
//                }
//            });
//            mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(IMediaPlayer mp) {
//                    if (playingListManager.getCurMusicMessage() != null) {
//                        MusicMessage musicMessage = playingListManager.getCurMusicMessage();
//                        if (musicMessage.getPlayProgress() > 0) {
//                            isSeekTo = true;
//                            mMediaPlayer.seekTo(musicMessage.getPlayProgress());
//                        } else {
//                            mMediaPlayer.start();
//                        }
//                        //设置当播放的状态
//                        audioPlayerManager.setMusicPlayStatus(AudioPlayerManager.PLAYING);
//                        //发送play的广播
//                        Intent playIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAY_MUSIC);
//                        //playIntent.putExtra(MusicMessage.KEY, musicMessage);
//                        playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                        sendBroadcast(playIntent);
//                    }
//                }
//            });
//
//            if (mPlayerThread == null) {
//                mPlayerThread = new Thread(new PlayerRunable());
//                mPlayerThread.start();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            playError();
//        }
//    }
//
//    /**
//     * 播放出错，发送消息，并在一秒后自动播放下一首
//     */
//    private void playError() {
//        //发送播放错误广播
//        Intent errorIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYERROR_MUSIC);
//        errorIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        sendBroadcast(errorIntent);
//        ToastUtil.showToast(getApplicationContext(), "播放歌曲出错，1秒后播放下一首");
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //播放下一首
//                nextMusic();
//            }
//        }, 1000);
//    }
//
//    /**
//     * 播放线程
//     */
//    private class PlayerRunable implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    Thread.sleep(100);//方便后面用来刷新歌词
//                    if (!isSeekTo && mMediaPlayer != null && mMediaPlayer.isPlaying()) {
//
//                        if (playingListManager.getCurMusicMessage() != null) {
//                            playingListManager.getCurMusicMessage().setPlayProgress(mMediaPlayer.getCurrentPosition());
//                            //发送正在播放中的广播
//                            Intent playingIntent = new Intent(AudioBroadcastReceiver.ACTION_SERVICE_PLAYING_MUSIC);
//                            //playingIntent.putExtra(MusicMessage.KEY, mHPApplication.getCurMusicMessage());
//                            playingIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                            sendBroadcast(playingIntent);
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     * 释放播放器
//     */
//    private void releasePlayer() {
//        audioPlayerManager.setMusicPlayStatus(AudioPlayerManager.STOP);
//        if (mPlayerThread != null) {
//            mPlayerThread = null;
//        }
//        if (mMediaPlayer != null) {
//            if (mMediaPlayer.isPlaying()) {
//                mMediaPlayer.stop();
//            }
//            mMediaPlayer.reset();
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//        System.gc();
//    }
//
//    /**
//     * 重置播放数据进度
//     */
//    private void resetPlayDataProgress() {
//        MusicMessage curMusicMessage = playingListManager.getCurMusicMessage();
//        if (curMusicMessage != null) {
//            curMusicMessage.setPlayProgress(0);
//        }
//
//    }
//}
