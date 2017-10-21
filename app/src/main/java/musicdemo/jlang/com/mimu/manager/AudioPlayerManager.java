package musicdemo.jlang.com.mimu.manager;

import android.content.Context;

import java.util.Random;

import musicdemo.jlang.com.mimu.ApplicationEx;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.greendao.entity.MusicPlayingList;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/18.
 */

public class AudioPlayerManager {
    private static AudioPlayerManager instance;

    private Context mContext;
    private ApplicationEx mApplicationEx;
    private PlayingListManager playingListManager;


    /**
     * 正在播放
     */
    public static final int PLAYING = 0;
    /**
     * 暂停
     */
    public static final int PAUSE = 1;
    /**
     * 停止
     */
    public static final int STOP = 2;
    /**
     * 播放在线音乐
     */
    public static final int PLAY_NET = 3;

    public static AudioPlayerManager getInstance(Context mContext, ApplicationEx mApplicationEx) {
        if (instance == null) {
            instance = new AudioPlayerManager(mContext, mApplicationEx);
        }
        return instance;
    }

    private AudioPlayerManager(Context mContext, ApplicationEx mApplicationEx) {
        this.mContext = mContext;
        this.mApplicationEx = mApplicationEx;
        playingListManager = PlayingListManager.getInstance();
    }

    /***
     * 初始化播放歌曲数据
     */
    public void initSongInfoData() {
//        //从本地文件中获取上次的播放歌曲列表
//        List<AudioInfo> curAudioInfos = mApplicationEx.getCurAudioInfos();
//        if (curAudioInfos != null && curAudioInfos.size() > 0) {
//            String playInfoHashID = mApplicationEx.getPlayIndexHashID();
//            //
//            if (playInfoHashID == null || playInfoHashID.equals("")) {
//
//                resetData();
//
//                //发送空数据广播
//                Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULL_MUSIC);
//                nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                mContext.sendBroadcast(nullIntent);
//
//                return;
//            }
//            boolean flag = false;
//            for (int i = 0; i < curAudioInfos.size(); i++) {
//                AudioInfo temp = curAudioInfos.get(i);
//                if (temp.getHash().equals(playInfoHashID)) {
//                    flag = true;
//
//
//                    //发送init的广播
//                    AudioMessage curAudioMessage = new AudioMessage();
//                    curAudioMessage.setAudioInfo(temp);
//
//
//                    mApplicationEx.setCurAudioMessage(curAudioMessage);
//                    mApplicationEx.setCurAudioInfo(temp);
//
//                    Intent initIntent = new Intent(AudioBroadcastReceiver.ACTION_INIT_MUSIC);
//                    initIntent.putExtra(AudioMessage.KEY, curAudioMessage);
//                    initIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                    mContext.sendBroadcast(initIntent);
//
//
//                }
//            }
//            if (!flag) {
//                resetData();
//            }
//        } else {
//            resetData();
//            //发送空数据广播
//            Intent nullIntent = new Intent(AudioBroadcastReceiver.ACTION_NULL_MUSIC);
//            nullIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//            mContext.sendBroadcast(nullIntent);
//        }
    }


    /**
     *
     */
    private void resetData() {
        //清空之前的播放数据
        setMusicPlayStatus(STOP);
        mApplicationEx.setCurMusicInfos(null);
        mApplicationEx.setPlayingIndex(-1);
        mApplicationEx.setCurAudioMessage(null);
    }


    /**
     * 上一首
     *
     * @param playModel
     */
    public MusicInfo preMusic(int playModel) {
        if (mApplicationEx.getCurMusicInfo() == null || mApplicationEx.getCurAudioMessage() == null || mApplicationEx.getCurMusicInfos() == null) {
            return null;
        }
        //获取播放索引
        int playIndex = getCurPlayIndex();
        if (playIndex == -1) {
            return null;
        }
        switch (playModel) {
            case 0:
                // 顺序播放
                playIndex--;
                if (playIndex < 0) {
                    return null;
                }
                if (mApplicationEx.getCurMusicInfos().size() > 0) {
                    return mApplicationEx.getCurMusicInfos().get(playIndex);
                }
                break;
            case 1:
                // 随机播放
                playIndex = new Random().nextInt(mApplicationEx.getCurMusicInfos().size());
                if (mApplicationEx.getCurMusicInfos().size() > 0) {
                    return mApplicationEx.getCurMusicInfos().get(playIndex);
                }
                break;
            case 2:
                // 循环播放
                playIndex--;
                if (playIndex < 0) {
                    playIndex = 0;
                }
                if (playIndex >= mApplicationEx.getCurMusicInfos().size()) {
                    playIndex = 0;
                }
                if (mApplicationEx.getCurMusicInfos().size() != 0) {
                    return mApplicationEx.getCurMusicInfos().get(playIndex);
                }
                break;
            case 3:
                // 单曲播放
                return mApplicationEx.getCurMusicInfos().get(playIndex);
        }
        return null;
    }


    /**
     * 下一首
     *
     * @param playModel 播放模式
     * @return
     */
    public MusicPlayingList nextMusic(int playModel) {
        if (playingListManager.getCurAudioMessage() == null || playingListManager.getCurrentPlayingList() == null) {
            return null;
        }
        //获取播放索引
        int playIndex = getCurPlayIndex();
        if (playIndex == -1) {
            return null;
        }
        MusicPlayingList playingList = null;
        switch (playModel) {
            case 0:
                // 顺序播放
                playIndex++;
                if (playIndex >= playingListManager.getCurrentPlayingList().size()) {
                    playIndex--;
                } else {
                    if (playingListManager.getCurrentPlayingList().size() > 0) {
                        playingList = playingListManager.getCurrentPlayingList().get(playIndex);
                    }
                }
                break;
            case 1:
                // 随机播放
                playIndex = new Random().nextInt(playingListManager.getCurrentPlayingList().size());
                if (playingListManager.getCurrentPlayingList().size() > 0) {
                    playingList = playingListManager.getCurrentPlayingList().get(playIndex);
                }
                break;
            case 2:
                // 循环播放
                playIndex++;
                if (playIndex >= playingListManager.getCurrentPlayingList().size()) {
                    playIndex = 0;
                }
                if (playingListManager.getCurrentPlayingList().size() > 0) {
                    playingList = playingListManager.getCurrentPlayingList().get(playIndex);
                }
                break;
            case 3:
                // 单曲播放
                playingList = playingListManager.getCurrentPlayingList().get(playIndex);
        }
        playingListManager.setCurrentPlayingIndex(playIndex);
        return playingList;
    }


    /**
     * 获取当前的播放索引
     *
     * @return 当前播放列表的索引
     */
    private int getCurPlayIndex() {
//        return mApplicationEx.getPlayingIndex();
        return playingListManager.getCurrentPlayingIndex();
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
