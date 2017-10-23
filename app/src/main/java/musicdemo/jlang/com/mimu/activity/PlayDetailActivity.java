package musicdemo.jlang.com.mimu.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.bean.MusicMessage;
import musicdemo.jlang.com.mimu.bean.MusicPlayInfo;
import musicdemo.jlang.com.mimu.event.message.EventMusicAction;
import musicdemo.jlang.com.mimu.manager.MusicPlayInfoManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayerManager;
import musicdemo.jlang.com.mimu.util.ATEUtil;
import musicdemo.jlang.com.mimu.util.music.MusicAction;
import musicdemo.jlang.com.mimu.view.PlayerSeekBar;

public class PlayDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgBg, mPlayPre, mPlayPlay, mPlayNext, mImgAlbum;
    private PlayerSeekBar mPlaySeek;
    private MusicPlayInfoManager musicPlayInfoManager;
    private MusicPlayerManager musicPlayerManager;
    private Toolbar mToolbar;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_detail);

        initView();
        initData();
        listener();
        registerEventBus();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImgBg = (ImageView) findViewById(R.id.img_bg);
        mPlayPre = (ImageView) findViewById(R.id.playing_pre);
        mPlayPlay = (ImageView) findViewById(R.id.playing_play);
        mPlayNext = (ImageView) findViewById(R.id.playing_next);
        mPlaySeek = (PlayerSeekBar) findViewById(R.id.play_seek);
        mImgAlbum = (ImageView) findViewById(R.id.img_album);
    }

    private void initData() {
        musicPlayerManager = MusicPlayerManager.getInstance(this);
        musicPlayInfoManager = MusicPlayInfoManager.getInstance();

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setMusicInfo();
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
                musicPlayerManager.playAction(MusicAction.ACTION_PRE_MUSIC, null, -1);
                break;
            case R.id.playing_play:
                musicPlayerManager.playAction();
                break;
            case R.id.playing_next:
                musicPlayerManager.playAction(MusicAction.ACTION_NEXT_MUSIC, null, -1);
                Log.d("TAG_DATA_DETAIL", musicPlayInfoManager.getMusicPlayListData().size() + "");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    private void setMusicInfo() {
        MusicMessage musicMessage = musicPlayInfoManager.getMusicMessage();
        if (musicMessage != null) {
            MusicPlayInfo musicInfo = musicMessage.getMusicInfo();
            if (musicInfo != null) {
                mPlaySeek.setMax((int) musicInfo.getDuration());
                actionBar.setTitle(musicInfo.getTitle());
                actionBar.setSubtitle(musicInfo.getArtistName());

                Glide.with(this)
                        .load(musicInfo.getAlbumPicUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                mImgAlbum.setImageDrawable(ATEUtil.getDefaultAlbumDrawable(PlayDetailActivity.this));
                                setBackgroundDrawable(mImgAlbum.getDrawable());
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                mImgAlbum.setImageBitmap(resource);
                                setBackgroundDrawable(mImgAlbum.getDrawable());
                            }
                        });
            }
        }
    }


    private void setBackgroundDrawable(Drawable result) {
        mImgBg.setImageDrawable(result);
//        if (result != null) {
//            if (mImgBg.getDrawable() != null) {
//                final TransitionDrawable td =
//                        new TransitionDrawable(new Drawable[]{mImgBg.getDrawable(), result});
//
//                mImgBg.setImageDrawable(td);
//                //去除过度绘制
//                td.setCrossFadeEnabled(true);
//                td.startTransition(200);
//
//            } else {
//                mImgBg.setImageDrawable(result);
//            }
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicActionEvent(EventMusicAction event) {
        switch (event.getAction()) {
            case MusicAction.ACTION_SERVICE_PLAY_MUSIC:
                Log.d("TAG_PROCESS_MAX", musicPlayInfoManager.getMusicDuration() + "");
                setMusicInfo();
                break;
            case MusicAction.ACTION_SERVICE_PLAYING_MUSIC:
                mPlaySeek.setProgress((int) musicPlayInfoManager.getMusicCurrentProcess());
                Log.d("TAG_PROCESS", musicPlayInfoManager.getMusicCurrentProcess() + "");
                break;
        }
    }
}
