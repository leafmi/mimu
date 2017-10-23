package musicdemo.jlang.com.mimu.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import musicdemo.jlang.com.mimu.ApplicationEx;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.adapter.LocalMusicFragmentAdapter;
import musicdemo.jlang.com.mimu.bean.MusicMessage;
import musicdemo.jlang.com.mimu.bean.MusicPlayInfo;
import musicdemo.jlang.com.mimu.event.message.EventMusicAction;
import musicdemo.jlang.com.mimu.fragment.AlbumsFragment;
import musicdemo.jlang.com.mimu.fragment.ArtistsFragment;
import musicdemo.jlang.com.mimu.fragment.FoldersFragment;
import musicdemo.jlang.com.mimu.fragment.SongsFragment;
import musicdemo.jlang.com.mimu.manager.AudioPlayerManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayInfoManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayerManager;
import musicdemo.jlang.com.mimu.permission.PermissionListener;
import musicdemo.jlang.com.mimu.receiver.AudioBroadcastReceiver;
import musicdemo.jlang.com.mimu.service.MusicPlayerService;
import musicdemo.jlang.com.mimu.util.ATEUtil;
import musicdemo.jlang.com.mimu.util.ListenerUtil;
import musicdemo.jlang.com.mimu.util.music.MusicAction;

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
    private ImageView mPlayBarArtist;
    private TextView musicName, artistName;

    private AudioPlayerManager audioPlayerManager;

    private MusicPlayerManager musicPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        initData();
        listener();
        registerEventBus();
        requestPermission();
    }

    private void intiView() {
        mLocalMusicTab = (TabLayout) findViewById(R.id.local_music_tab);
        mLocalMusicViewPager = (ViewPager) findViewById(R.id.local_music_viewPager);
        mLayoutPlayerContent = findViewById(R.id.layout_player_content);
        mPlayBarArtist = (ImageView) findViewById(R.id.play_bar_artist);
        musicName = (TextView) findViewById(R.id.music_name);
        artistName = (TextView) findViewById(R.id.artist_name);
        mBarPlay = findViewById(R.id.bar_play);
        mBarPause = findViewById(R.id.bar_pause);
        mBarNext = findViewById(R.id.bar_next);
    }

    private void initData() {
        //启动Music 服务
        Intent musiServiceIntent = new Intent(this, MusicPlayerService.class);
        ApplicationEx.getInstance().startService(musiServiceIntent);
        //设置当播放的状态
        musicPlayerManager = MusicPlayerManager.getInstance(this);
        musicPlayerManager.setMusicPlayStatus(AudioPlayerManager.STOP);

        audioPlayerManager = AudioPlayerManager.getInstance(this, ApplicationEx.getInstance());
        //获取播放列表信息
        MusicPlayInfoManager.getInstance().getPlayListDetailInfo();
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_player_content:
                startActivity(new Intent(this, PlayDetailActivity.class));
                break;
            case R.id.bar_play:
                musicPlayerManager.playAction();
                break;
            case R.id.bar_pause:
                musicPlayerManager.playAction();
                break;
            case R.id.bar_next:
                musicPlayerManager.playAction(MusicAction.ACTION_NEXT_MUSIC, null, -1);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicActionEvent(EventMusicAction event) {
        switch (event.getAction()) {
            case MusicAction.ACTION_NULL_MUSIC:
                mBarPause.setVisibility(View.INVISIBLE);
                mBarPlay.setVisibility(View.VISIBLE);
                break;
            case MusicAction.ACTION_INIT_MUSIC:
                break;
            case MusicAction.ACTION_SERVICE_PLAY_MUSIC:
                MusicMessage musicMessage = MusicPlayInfoManager.getInstance().getMusicMessage();
                MusicPlayInfo musicInfo = musicMessage.getMusicInfo();
                Glide.with(this)
                        .load(musicInfo.getAlbumPicUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                mPlayBarArtist.setImageDrawable(ATEUtil.getDefaultAlbumDrawable(MainActivity.this));
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                mPlayBarArtist.setImageBitmap(resource);
                            }
                        });
                musicName.setText(musicInfo.getTitle());
                artistName.setText(musicInfo.getArtistName());
                mBarPlay.setVisibility(View.INVISIBLE);
                mBarPause.setVisibility(View.VISIBLE);
                break;
            case MusicAction.ACTION_SERVICE_PLAYING_MUSIC:
                break;
            case MusicAction.ACTION_SERVICE_PAUSE_MUSIC:
                mBarPlay.setVisibility(View.VISIBLE);
                mBarPause.setVisibility(View.INVISIBLE);
                break;
            case MusicAction.ACTION_SERVICE_RESUME_MUSIC:
                mBarPlay.setVisibility(View.INVISIBLE);
                mBarPause.setVisibility(View.VISIBLE);
                break;
        }
    }
}
