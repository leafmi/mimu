package musicdemo.jlang.com.musicdemo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import musicdemo.jlang.com.musicdemo.ApplicationEx;
import musicdemo.jlang.com.musicdemo.R;
import musicdemo.jlang.com.musicdemo.adapter.LocalMusicFragmentAdapter;
import musicdemo.jlang.com.musicdemo.bean.AudioMessage;
import musicdemo.jlang.com.musicdemo.fragment.AlbumsFragment;
import musicdemo.jlang.com.musicdemo.fragment.ArtistsFragment;
import musicdemo.jlang.com.musicdemo.fragment.FoldersFragment;
import musicdemo.jlang.com.musicdemo.fragment.SongsFragment;
import musicdemo.jlang.com.musicdemo.manager.AudioPlayerManager;
import musicdemo.jlang.com.musicdemo.manager.PlayingListManager;
import musicdemo.jlang.com.musicdemo.permission.PermissionListener;
import musicdemo.jlang.com.musicdemo.receiver.AudioBroadcastReceiver;
import musicdemo.jlang.com.musicdemo.service.AudioPlayerService;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private List<String> mTitleList = new ArrayList<>(4);
    private List<Fragment> fragments = new ArrayList<>(4);

    private SongsFragment mSongsFragment;
    private ArtistsFragment mArtistsFragment;
    private AlbumsFragment mAlbumsFragment;
    private FoldersFragment mFoldersFragment;

    private TabLayout mLocalMusicTab;
    private ViewPager mLocalMusicViewPager;
    private View mLayoutPlayerContent, mBarPlay, mBarPause, mBarNext;


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

    /**
     * 播放列表Manager
     */
    private PlayingListManager playingListManager;
    private AudioPlayerManager audioPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        initData();
        listener();
        initService();
        requestPermission();
    }

    private void intiView() {
        mLocalMusicTab = (TabLayout) findViewById(R.id.local_music_tab);
        mLocalMusicViewPager = (ViewPager) findViewById(R.id.local_music_viewPager);
        mLayoutPlayerContent = findViewById(R.id.layout_player_content);
        mBarPlay = findViewById(R.id.bar_play);
        mBarPause = findViewById(R.id.bar_pause);
        mBarNext = findViewById(R.id.bar_next);
    }

    private void initData() {
        //启动Music 服务
        Intent playerServiceIntent = new Intent(this, AudioPlayerService.class);
        ApplicationEx.getInstance().startService(playerServiceIntent);


        //获取播放列表Manager
        playingListManager = PlayingListManager.getInstance();
        audioPlayerManager = AudioPlayerManager.getInstance(this, ApplicationEx.getInstance());

        //初始化Music 播放状态
        audioPlayerManager.setMusicPlayStatus(AudioPlayerManager.STOP);

        initViewPager();
        LocalMusicFragmentAdapter fragmentAdapter = new LocalMusicFragmentAdapter(getSupportFragmentManager(), fragments, mTitleList);
        fragmentAdapter.notifyDataSetChanged();
        mLocalMusicViewPager.setAdapter(fragmentAdapter);
        mLocalMusicViewPager.setOffscreenPageLimit(1);
        mLocalMusicTab.setTabMode(TabLayout.MODE_FIXED);
        mLocalMusicTab.setupWithViewPager(mLocalMusicViewPager);

        mLocalMusicViewPager.setCurrentItem(0);
    }

    private void listener() {
        mLayoutPlayerContent.setOnClickListener(this);
        mBarPlay.setOnClickListener(this);
        mBarPause.setOnClickListener(this);
        mBarNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_player_content:
                startActivity(new Intent(this, PlayDetailActivity.class));
                break;
            case R.id.bar_play:
                int audioPlayStatus = audioPlayerManager.getMusicPlayStatus();
                Intent playIntent = null;
                if (audioPlayStatus == AudioPlayerManager.PAUSE) {
                    playIntent = new Intent(AudioBroadcastReceiver.ACTION_RESUME_MUSIC);
                } else if (audioPlayStatus == AudioPlayerManager.STOP) {
                    playIntent = new Intent(AudioBroadcastReceiver.ACTION_PLAY_MUSIC);
                }
                if (playIntent != null) {
                    playIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    playIntent.putExtra(AudioMessage.KEY, playingListManager.getCurAudioMessage());
                    sendBroadcast(playIntent);
                }
                break;
            case R.id.bar_pause:
                if (audioPlayerManager.getMusicPlayStatus() == AudioPlayerManager.PLAYING) {
                    Intent pauseIntent = new Intent(AudioBroadcastReceiver.ACTION_PAUSE_MUSIC);
                    pauseIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(pauseIntent);
                }
                break;
            case R.id.bar_next:
                Intent nextIntent = new Intent(AudioBroadcastReceiver.ACTION_NEXT_MUSIC);
                nextIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(nextIntent);
                break;
        }
    }

    //滑动布局
    private void initViewPager() {
        mTitleList.add("单曲");
        mTitleList.add("歌手");
        mTitleList.add("专辑");
        mTitleList.add("文件夹");

        if (mSongsFragment == null) {
            mSongsFragment = new SongsFragment();
            fragments.add(mSongsFragment);
        }
        if (mArtistsFragment == null) {
            mArtistsFragment = new ArtistsFragment();
            fragments.add(mArtistsFragment);
        }
        if (mAlbumsFragment == null) {
            mAlbumsFragment = new AlbumsFragment();
            fragments.add(mAlbumsFragment);
        }
        if (mFoldersFragment == null) {
            mFoldersFragment = new FoldersFragment();
            fragments.add(mFoldersFragment);
        }

    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        requestRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , new PermissionListener() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for (String permission : deniedPermission) {
                            Toast.makeText(MainActivity.this, "被拒绝权限：" + permission, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            case AudioBroadcastReceiver.ACTION_NULL_MUSIC:
                mBarPause.setVisibility(View.INVISIBLE);
                mBarPlay.setVisibility(View.VISIBLE);
                break;
            case AudioBroadcastReceiver.ACTION_INIT_MUSIC:
                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_PLAY_MUSIC:
                mBarPlay.setVisibility(View.INVISIBLE);
                mBarPause.setVisibility(View.VISIBLE);
                Log.d("TAG_MUSIC", "开始播放音乐");
                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_PAUSE_MUSIC:
                mBarPlay.setVisibility(View.VISIBLE);
                mBarPause.setVisibility(View.INVISIBLE);
                Log.d("TAG_MUSIC", "暂停音乐");
                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_RESUME_MUSIC:
                mBarPlay.setVisibility(View.INVISIBLE);
                mBarPause.setVisibility(View.VISIBLE);
                Log.d("TAG_MUSIC", "唤醒音乐");
                break;
            case AudioBroadcastReceiver.ACTION_SERVICE_PLAYING_MUSIC:
                break;
        }
    }
}
