package musicdemo.jlang.com.mimu.manager;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

import musicdemo.jlang.com.mimu.ApplicationEx;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.bean.MusicMessage;
import musicdemo.jlang.com.mimu.bean.MusicPlayInfo;
import musicdemo.jlang.com.mimu.event.message.EventMusicAction;
import musicdemo.jlang.com.mimu.greendao.entity.MusicPlayingInfo;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;
import musicdemo.jlang.com.mimu.util.music.MusicAction;
import musicdemo.jlang.com.mimu.util.music.MusicPlaySate;

/**
 * Created by JLang on 2017/10/21.
 */

public class MusicPlayerManager {
    private static MusicPlayerManager instance;
    private MusicPlayInfoManager musicPlayInfoManager;

    private Context mContext;

    public static MusicPlayerManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new MusicPlayerManager(mContext);
        }
        return instance;
    }

    public MusicPlayerManager(Context mContext) {
        this.mContext = mContext;
        musicPlayInfoManager = MusicPlayInfoManager.getInstance();
    }


    public void playAction(int action, MusicPlayInfo musicInfo, int musicType) {
        MusicMessage musicMessage = null;
        boolean isNeedMessage = true;
        switch (action) {
            case MusicAction.ACTION_PLAY_MUSIC:
                musicMessage = new MusicMessage(musicInfo, musicType);
                break;
            case MusicAction.ACTION_RESUME_MUSIC:
                musicMessage = MusicPlayInfoManager.getInstance().getMusicMessage();
                break;
            case MusicAction.ACTION_PAUSE_MUSIC:
            case MusicAction.ACTION_PRE_MUSIC:
            case MusicAction.ACTION_NEXT_MUSIC:
                isNeedMessage = false;
                break;
        }
        if (musicMessage != null) {
            EventBus.getDefault().post(new EventMusicAction(action, musicMessage));
        } else {
            if (!isNeedMessage) {
                EventBus.getDefault().post(new EventMusicAction(action));
            }
        }
    }


    public void playAction(MusicPlayInfo musicInfo, int musicType) {
        MusicMessage musicMessage = MusicPlayInfoManager.getInstance().getMusicMessage();
        if (musicMessage != null && musicInfo != null) {
            if (musicMessage.getMusicInfo().getData().equals(musicInfo.getData())) {
                //根据状态执行操作
                switch (getMusicPlayStatus()) {
                    case MusicPlaySate.PLAYING:
                        playAction(MusicAction.ACTION_PAUSE_MUSIC, null, musicType);
                        break;
                    case MusicPlaySate.PAUSE:
                        playAction(MusicAction.ACTION_RESUME_MUSIC, null, musicType);
                        break;
                    case MusicPlaySate.STOP:
                        playAction(MusicAction.ACTION_RESUME_MUSIC, null, musicType);
                        break;
                }
                return;
            }
        }
        //播放新的音乐
        playAction(MusicAction.ACTION_PLAY_MUSIC, musicInfo, musicType);
    }

    public void playAction() {
        playAction(getMusicPlayStatus());

    }

    public void playAction(int action) {
        switch (action) {
            case MusicPlaySate.PLAYING:
                playAction(MusicAction.ACTION_PAUSE_MUSIC, null, -1);
                break;
            case MusicPlaySate.PAUSE:
                playAction(MusicAction.ACTION_RESUME_MUSIC, null, -1);
                break;
            case MusicPlaySate.STOP:
                playAction(MusicAction.ACTION_RESUME_MUSIC, null, -1);
                break;
        }
    }

    /**
     * 上一首
     *
     * @return
     */
    public MusicPlayInfo preMusic() {
        List<MusicPlayInfo> musicInfos;
        if (musicPlayInfoManager != null) {
            musicInfos = musicPlayInfoManager.getMusicPlayListData();
        } else {
            return null;
        }
        int playModel = getMusicPlayModel();
        int size = musicInfos.size();
        //获取播放索引
        int playIndex = getCurPlayIndex();
        if (playIndex == -1) {
            return null;
        }
        MusicPlayInfo musicInfo = null;
        switch (playModel) {
            case 0:
                // 顺序播放
                playIndex--;
                if (playIndex < 0) {
                    playIndex++;
                } else {
                    if (size > 0) {
                        musicInfo = musicInfos.get(playIndex);
                    }
                }
                break;
            case 1:
                // 随机播放
                playIndex = new Random().nextInt(size);
                if (size > 0) {
                    musicInfo = musicInfos.get(playIndex);
                }
                break;
            case 2:
                // 循环播放
                playIndex--;
                if (playIndex < 0) {
                    playIndex = size;
                }
                if (size > 0) {
                    musicInfo = musicInfos.get(playIndex);
                }
                break;
            case 3:
                // 单曲播放
                musicInfo = musicInfos.get(playIndex);
        }
        musicPlayInfoManager.setCurrentPlayingIndex(playIndex);
        return musicInfo;
    }

    /**
     * 下一首
     *
     * @return
     */
    public MusicPlayInfo nextMusic() {
        List<MusicPlayInfo> musicInfos;
        if (musicPlayInfoManager != null) {
            musicInfos = musicPlayInfoManager.getMusicPlayListData();
        } else {
            return null;
        }
        int playModel = getMusicPlayModel();
        int size = musicInfos.size();
        //获取播放索引
        int playIndex = getCurPlayIndex();
        if (playIndex == -1) {
            return null;
        }
        MusicPlayInfo musicInfo = null;
        switch (playModel) {
            case 0:
                // 顺序播放
                playIndex++;
                if (playIndex >= size) {
                    playIndex--;
                } else {
                    if (size > 0) {
                        musicInfo = musicInfos.get(playIndex);
                    }
                }
                break;
            case 1:
                // 随机播放
                playIndex = new Random().nextInt(size);
                if (size > 0) {
                    musicInfo = musicInfos.get(playIndex);
                }
                break;
            case 2:
                // 循环播放
                playIndex++;
                if (playIndex >= size) {
                    playIndex = 0;
                }
                if (size > 0) {
                    musicInfo = musicInfos.get(playIndex);
                }
                break;
            case 3:
                // 单曲播放
                musicInfo = musicInfos.get(playIndex);
        }
        musicPlayInfoManager.setCurrentPlayingIndex(playIndex);
        return musicInfo;
    }


    /**
     * 获取当前的播放索引
     *
     * @return 当前播放列表的索引
     */
    private int getCurPlayIndex() {
//        return mApplicationEx.getPlayingIndex();
        return musicPlayInfoManager.getCurrentPlayingIndex();
    }

    //获取音乐播放状态
    public int getMusicPlayStatus() {
        return PreferencesUtility.getInstance(mContext).getMusicPlayStatus();
    }

    //设置音乐播放状态
    public void setMusicPlayStatus(int playStatus) {
        PreferencesUtility.getInstance(mContext).setMusicPlayStatus(playStatus);
    }

    /**
     * 当前是否正在播放音乐
     *
     * @return true：正在播放、false：未播放
     */
    public boolean isMusicPlaying() {
        return getMusicPlayStatus() == MusicPlaySate.PLAYING;
    }


    public int getMusicPlayModel() {
        return PreferencesUtility.getInstance(mContext).getMusicPlayModel();
    }

    public void setMusicPlayModel(int playModel) {
        PreferencesUtility.getInstance(mContext).setMusicPlayModel(playModel);
    }
}
