package musicdemo.jlang.com.mimu.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.adapter.SongsAdapter;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.dataloader.SongLoader;
import musicdemo.jlang.com.mimu.greendao.SQLiteOpenHelperManager;
import musicdemo.jlang.com.mimu.greendao.entity.LocalMusicInfo;
import musicdemo.jlang.com.mimu.greendao.gen.DaoSession;
import musicdemo.jlang.com.mimu.greendao.gen.LocalMusicInfoDao;
import musicdemo.jlang.com.mimu.manager.MusicPlayInfoManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayerManager;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/16.
 */

public class SongsFragment extends Fragment implements RecyclerViewOnItemClickListener {
    private View root;
    private RecyclerView mSongsRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_songs, null);
        }
        intiView();
        initData();
        listener();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void intiView() {
        mSongsRecyclerView = (RecyclerView) root.findViewById(R.id.songs_recycler_view);
    }


    private void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mSongsRecyclerView.setLayoutManager(mLayoutManager);
        getAllSongs();
    }

    private void listener() {

    }


    /**
     * 获取所有歌曲列表（过滤了Artist为：<unknown>音乐）
     */
    private void getAllSongs() {
        SongLoader.getAllSongs(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MusicInfo>>() {
                    @Override
                    public void accept(List<MusicInfo> songs) throws Exception {
                        SongsAdapter songsAdapter = new SongsAdapter(getActivity(), songs);
                        songsAdapter.setOnItemClickListener(SongsFragment.this);
                        mSongsRecyclerView.setAdapter(songsAdapter);

                        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(getActivity(), true);
                        LocalMusicInfoDao localMusicInfoDao = daoSession.getLocalMusicInfoDao();
                        List<LocalMusicInfo> localMusicInfos = new ArrayList<LocalMusicInfo>();
                        for (int i = 0; i < songs.size(); i++) {
                            MusicInfo musicInfo = songs.get(i);
                            localMusicInfos.add(new LocalMusicInfo(musicInfo.getId(),musicInfo.getPath()
                                    ,musicInfo.getSize()
                                    ,System.currentTimeMillis()
                                    ,System.currentTimeMillis()
                                    ,musicInfo.duration
                                    ,musicInfo.title
                                    ,12L
                                    ,12L
                            ));
                        }
                        localMusicInfoDao.insertOrReplaceInTx(localMusicInfos);


                    }
                });
    }

    private int lastPlayPosition = -1;

    @Override
    public void onItemClick(View view, int position, Object object) {
//        List<MusicInfo> musicInfos = (List<MusicInfo>) object;
//        Intent playIntent = new Intent();
//        MusicInfo musicInfo = musicInfos.get(position);
//
//
//
//        int audioPlayStatus = AudioPlayerManager.getInstance(getActivity(), ApplicationEx.getInstance()).getMusicPlayStatus();
//
//        PlayingListManager playingListManager = PlayingListManager.getInstance();
//        MusicMessage curAudioMessage = playingListManager.getCurMusicMessage();
//        if (curAudioMessage != null && curAudioMessage.getMusicData().equals(musicInfo.getPath())) {
//            if (audioPlayStatus == AudioPlayerManager.PLAYING) {
//                playIntent.setAction(AudioBroadcastReceiver.ACTION_PAUSE_MUSIC);
//            } else if (audioPlayStatus == AudioPlayerManager.PAUSE) {
//                playIntent.setAction(AudioBroadcastReceiver.ACTION_RESUME_MUSIC);
//                playIntent.putExtra(MusicMessage.KEY, playingListManager.getCurMusicMessage());
//            } else if (audioPlayStatus == AudioPlayerManager.STOP) {
//                playIntent = new Intent(AudioBroadcastReceiver.ACTION_PLAY_MUSIC);
//                playIntent.putExtra(MusicMessage.KEY, playingListManager.getCurMusicMessage());
//            }
//        } else {
//            playIntent.setAction(AudioBroadcastReceiver.ACTION_PLAY_MUSIC);
//            MusicMessage audioMessage = new MusicMessage();
//            audioMessage.setMusicData(musicInfo.getPath());
//            audioMessage.setMusicType(MusicInfo.LOCAL);
//            playIntent.putExtra(MusicMessage.KEY, audioMessage);
//        }
//
//        playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        getActivity().sendBroadcast(playIntent);
//
//        //如果当前数据歌单
//        if (PreferencesUtility.getInstance(getActivity()).getCurrentPlayingListSourceId() != -1) {
//            playingListManager.setCurrentPlayingIndex(position);
//
//            //创建新的播放列表
//            playingListManager.addLocalPlayingList(musicInfos, -1);
//        } else {
//            //计算播放Id
//            playingListManager.calcCurrentPLayingIndex(musicInfo.getPath());
//        }
        List<MusicInfo> musicInfos = (List<MusicInfo>) object;
        MusicInfo musicInfo = musicInfos.get(position);
        MusicPlayerManager.getInstance(getActivity()).playAction(musicInfo, MusicInfo.LOCAL);
        //如果当前数据歌单
        if (PreferencesUtility.getInstance(getActivity()).getCurrentPlayingListSourceId() != -1) {
            MusicPlayInfoManager.getInstance().setCurrentPlayingIndex(position);
            //创建新的播放列表
            MusicPlayInfoManager.getInstance().addLocalPlayingList(musicInfos, -1);
        } else {
            //计算播放Id
            MusicPlayInfoManager.getInstance().calcCurrentPLayingIndex(musicInfo.getPath());
        }
    }


}
