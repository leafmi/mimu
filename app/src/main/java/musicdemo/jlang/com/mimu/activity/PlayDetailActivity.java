package musicdemo.jlang.com.mimu.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.bean.MusicMessage;
import musicdemo.jlang.com.mimu.bean.MusicPlayInfo;
import musicdemo.jlang.com.mimu.event.message.EventMusicAction;
import musicdemo.jlang.com.mimu.fragment.PlayQueueFragment;
import musicdemo.jlang.com.mimu.manager.MusicPlayInfoManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayerManager;
import musicdemo.jlang.com.mimu.util.ATEUtil;
import musicdemo.jlang.com.mimu.util.BitmapUtils;
import musicdemo.jlang.com.mimu.util.music.MusicAction;
import musicdemo.jlang.com.mimu.view.PlayerSeekBar;

public class PlayDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgBg, mPlayPre, mPlayPlay, mPlayNext, mImgAlbum, mPlayQueue;
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
        startRotate();
        Log.i("js_flag","onCreate");

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImgBg = (ImageView) findViewById(R.id.img_bg);
        mPlayPre = (ImageView) findViewById(R.id.playing_pre);
        mPlayPlay = (ImageView) findViewById(R.id.playing_play);
        mPlayNext = (ImageView) findViewById(R.id.playing_next);
        mPlaySeek = (PlayerSeekBar) findViewById(R.id.play_seek);
        mImgAlbum = (ImageView) findViewById(R.id.img_album);
        mPlayQueue = (ImageView) findViewById(R.id.playing_queue);
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
        mPlayQueue.setOnClickListener(this);
        mPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicMessage musicMessage = musicPlayInfoManager.getMusicMessage();
                musicMessage.setPlayProgress(seekBar.getProgress());
                EventBus.getDefault().post(new EventMusicAction(MusicAction.ACTION_SEEKTO_MUSIC, musicMessage));
            }
        });
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
            case R.id.playing_queue:
                new PlayQueueFragment().show(getFragmentManager(),"da");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        stopRotate();
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
                                setBackgroundDrawable(ATEUtil.getDefaultAlbumDrawable(PlayDetailActivity.this));
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                mImgAlbum.setImageBitmap(resource);
                                setBackgroundBitmap(resource);
                            }
                        });
            }
        }
    }
    private void setBackgroundBitmap(Bitmap bitmap){
        mImgBg.setImageBitmap(BitmapUtils.getAssetsVideoGlassBmp(bitmap,musicPlayInfoManager.getMusicMessage().getMusicInfo().getTitle()));
    }

    private void setBackgroundDrawable(Drawable result) {
        mImgBg.setImageDrawable(result);
        if (result instanceof  BitmapDrawable)
            mImgBg.setImageBitmap(BitmapUtils.getAssetsVideoGlassBmp(((BitmapDrawable) result).getBitmap(),musicPlayInfoManager.getMusicMessage().getMusicInfo().getTitle()));

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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_empty, R.anim.pop_down_out);
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

    private ValueAnimator valueAnimator;
    private void startRotate(){
        if (valueAnimator!=null){
            valueAnimator.start();
        }else {
            valueAnimator = new ValueAnimator();
            valueAnimator.setRepeatCount(-1);
            valueAnimator.setDuration(10000);
            valueAnimator.setObjectValues("");
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setEvaluator(new TypeEvaluator() {
                @Override
                public Object evaluate(float fraction, Object startValue, Object endValue) {
                    mImgAlbum.setRotation(fraction * 360);
                    return null;
                }
            });
            valueAnimator.start();
        }
    }
    private void stopRotate(){
        if (valueAnimator!=null){
            valueAnimator.cancel();
        }
    }
}
