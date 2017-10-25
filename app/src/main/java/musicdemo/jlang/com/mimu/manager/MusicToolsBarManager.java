package musicdemo.jlang.com.mimu.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.activity.MainActivity;
import musicdemo.jlang.com.mimu.bean.MusicMessage;
import musicdemo.jlang.com.mimu.bean.MusicPlayInfo;
import musicdemo.jlang.com.mimu.event.message.EventCancelMusicToolsBar;
import musicdemo.jlang.com.mimu.event.message.EventMusicAction;
import musicdemo.jlang.com.mimu.event.message.EventMusicFavourite;
import musicdemo.jlang.com.mimu.receiver.MusicToolsBarReceiver;
import musicdemo.jlang.com.mimu.util.Constants;
import musicdemo.jlang.com.mimu.util.IntentExtraName;
import musicdemo.jlang.com.mimu.util.music.MusicAction;
import musicdemo.jlang.com.mimu.util.music.MusicPlaySate;

/**
 * Music Tools Bar
 * Created by JLang on 2017/10/23.
 */

public class MusicToolsBarManager {
    private Context mContext;
    private NotificationManager mNotificationManager;
    private MusicToolsBarReceiver musicToolsBarReceiver;
    private RemoteViews mRemoteViews;
    private RemoteViews mSmallRemoteViews;


    public void onCreate(Context context) {
        this.mContext = context;
        registerMusicReceiver();
        registerEventBus();
    }

    public void onDestroy() {
        cancelMusicToolsBar();
        unregisterMusicReceiver();
        unregisterEventBus();
    }


    /**
     * 取消ToolsBar
     */
    private void cancelMusicToolsBar() {
        try {
            if (mNotificationManager != null) {
                mNotificationManager.cancel(Constants.MUSIC_TOOLS_BAR_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册Music 广播
     */
    private void registerMusicReceiver() {
        musicToolsBarReceiver = new MusicToolsBarReceiver();
        IntentFilter mMusicIntentFilter = new IntentFilter();
        mMusicIntentFilter.addAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_PLAY_MUSIC);
        mMusicIntentFilter.addAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_RESUME_MUSIC);
        mMusicIntentFilter.addAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_PAUSE_MUSIC);
        mMusicIntentFilter.addAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_PRE_MUSIC);
        mMusicIntentFilter.addAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_NEXT_MUSIC);
        mMusicIntentFilter.addAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_FAV_MUSIC);
        mMusicIntentFilter.addAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_CLOSE);
        mContext.registerReceiver(musicToolsBarReceiver, mMusicIntentFilter);
    }

    /**
     * 注销Music 广播
     */
    private void unregisterMusicReceiver() {
        if (musicToolsBarReceiver != null) {
            mContext.unregisterReceiver(musicToolsBarReceiver);
        }
    }

    /**
     * 注册EventBus
     */
    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 注销EventBus
     */
    private void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    public void disPlayMusicToolsBar(MusicPlayInfo musicPlayInfo) {
        crateMusicRemoteView(musicPlayInfo);
        createMusicSmallRemoteView(musicPlayInfo);
        updateToolsBar();
    }

    private void updateToolsBar() {
        if (mRemoteViews != null && mSmallRemoteViews != null) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setWhen(System.currentTimeMillis())
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)//设置优先级
                    .setOngoing(true)
                    .setCustomBigContentView(mRemoteViews)
                    .setCustomContentView(mSmallRemoteViews)
                    .setSmallIcon(R.mipmap.ic_launcher);

            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            notification.flags |= Notification.FLAG_NO_CLEAR;

            mNotificationManager.notify(Constants.MUSIC_TOOLS_BAR_ID, notification);
        }
    }

    /**
     * 改变Music 状态
     *
     * @param resId 状态图标资源Id
     */
    private void changeMusicStatus(int resId) {
        if (mRemoteViews != null) {
            mRemoteViews.setImageViewResource(R.id.img_play, resId);
            mSmallRemoteViews.setImageViewResource(R.id.img_play, resId);
            updateToolsBar();
        }
    }

    private void crateMusicRemoteView(MusicPlayInfo musicPlayInfo) {
        if (musicPlayInfo == null || mContext == null) {
            return;
        }
        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.layout_music_tools_bar);
        mRemoteViews.setTextViewText(R.id.tv_title, musicPlayInfo.getTitle());
        mRemoteViews.setTextViewText(R.id.tv_artist, musicPlayInfo.getArtistName());

        Glide.with(mContext)
                .load(musicPlayInfo.getAlbumPicUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        mRemoteViews.setImageViewResource(R.id.img_album, R.drawable.icon_album_default);
                        updateToolsBar();
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mRemoteViews.setImageViewBitmap(R.id.img_album, resource);
                        updateToolsBar();
                    }
                });
        Intent intent = new Intent(mContext, MainActivity.class);
        // 点击跳转到主界面
        PendingIntent intent_go = PendingIntent.getActivity(mContext, 5, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.notice, intent_go);

        // 上一曲
        Intent prv = new Intent();
        prv.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_PRE_MUSIC);
        PendingIntent intent_prev = PendingIntent.getBroadcast(mContext, 1, prv,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.img_prev, intent_prev);

        // 下一曲
        Intent next = new Intent();
        next.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_NEXT_MUSIC);
        PendingIntent intent_next = PendingIntent.getBroadcast(mContext, 3, next,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.img_next, intent_next);

        int musicPlayStatus = MusicPlayerManager.getInstance(mContext).getMusicPlayStatus();
        //暂停
        if (musicPlayStatus == MusicPlaySate.PLAYING) {
            Intent playorpause = new Intent();
            playorpause.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_PAUSE_MUSIC);
            PendingIntent intent_play = PendingIntent.getBroadcast(mContext, 2,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            mRemoteViews.setOnClickPendingIntent(R.id.img_play, intent_play);
            mRemoteViews.setImageViewResource(R.id.img_play, R.drawable.play_btn_pause);
        } else {
            //唤醒播放
            Intent playorpause = new Intent();
            playorpause.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_RESUME_MUSIC);
            PendingIntent intent_play = PendingIntent.getBroadcast(mContext, 6,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            mRemoteViews.setOnClickPendingIntent(R.id.img_play, intent_play);
            mRemoteViews.setImageViewResource(R.id.img_play, R.drawable.play_btn_pause);
        }
        // 设置最喜爱
        Intent fav = new Intent();
        fav.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_FAV_MUSIC);
        PendingIntent intent_fav = PendingIntent.getBroadcast(mContext, 4, fav,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.img_fav, intent_fav);
        if (musicPlayInfo.isFavourite()) {
            mRemoteViews.setImageViewResource(R.id.img_fav, R.drawable.play_icn_loved);
        } else {
            mRemoteViews.setImageViewResource(R.id.img_fav, R.drawable.play_icn_love);
        }


        Intent close = new Intent();
        close.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_CLOSE);
        PendingIntent intent_close = PendingIntent.getBroadcast(mContext, 7, close,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.img_close, intent_close);
    }

    private void createMusicSmallRemoteView(MusicPlayInfo musicPlayInfo) {
        if (musicPlayInfo == null || mContext == null) {
            return;
        }
        mSmallRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.layout_music_small_tools_bar);
        mSmallRemoteViews.setTextViewText(R.id.tv_title, musicPlayInfo.getTitle());
        mSmallRemoteViews.setTextViewText(R.id.tv_artist, musicPlayInfo.getArtistName());

        Glide.with(mContext)
                .load(musicPlayInfo.getAlbumPicUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        mSmallRemoteViews.setImageViewResource(R.id.img_album, R.drawable.icon_album_default);
                        updateToolsBar();
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mSmallRemoteViews.setImageViewBitmap(R.id.img_album, resource);
                        updateToolsBar();
                    }
                });
        Intent intent = new Intent(mContext, MainActivity.class);
        // 点击跳转到主界面
        PendingIntent intent_go = PendingIntent.getActivity(mContext, 5, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mSmallRemoteViews.setOnClickPendingIntent(R.id.notice, intent_go);


        // 下一曲
        Intent next = new Intent();
        next.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_NEXT_MUSIC);
        PendingIntent intent_next = PendingIntent.getBroadcast(mContext, 3, next,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mSmallRemoteViews.setOnClickPendingIntent(R.id.img_next, intent_next);

        int musicPlayStatus = MusicPlayerManager.getInstance(mContext).getMusicPlayStatus();
        //暂停
        if (musicPlayStatus == MusicPlaySate.PLAYING) {
            Intent playorpause = new Intent();
            playorpause.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_PAUSE_MUSIC);
            PendingIntent intent_play = PendingIntent.getBroadcast(mContext, 2,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            mRemoteViews.setOnClickPendingIntent(R.id.img_play, intent_play);
            mRemoteViews.setImageViewResource(R.id.img_play, R.drawable.play_btn_pause);
        } else {
            //唤醒播放
            Intent playorpause = new Intent();
            playorpause.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_RESUME_MUSIC);
            PendingIntent intent_play = PendingIntent.getBroadcast(mContext, 6,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            mSmallRemoteViews.setOnClickPendingIntent(R.id.img_play, intent_play);
            mSmallRemoteViews.setImageViewResource(R.id.img_play, R.drawable.play_btn_pause);
        }

        Intent close = new Intent();
        close.setAction(MusicToolsBarReceiver.ACTION_TOOLS_BAR_CLOSE);
        PendingIntent intent_close = PendingIntent.getBroadcast(mContext, 7, close,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mSmallRemoteViews.setOnClickPendingIntent(R.id.img_close, intent_close);
    }


    //**********************Event bus********************************


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicActionEvent(EventMusicAction event) {
        switch (event.getAction()) {
            case MusicAction.ACTION_SERVICE_PAUSE_MUSIC:
                changeMusicStatus(R.drawable.play_btn_play);
                break;
            case MusicAction.ACTION_SERVICE_RESUME_MUSIC:
                changeMusicStatus(R.drawable.play_btn_pause);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCancelMusicToolsBarEvent(EventCancelMusicToolsBar event) {
        cancelMusicToolsBar();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicFavouriteEvent(EventMusicFavourite event) {
        MusicMessage musicMessage = MusicPlayInfoManager.getInstance().getMusicMessage();
        if (musicMessage != null) {
            MusicPlayInfo musicInfo = musicMessage.getMusicInfo();
            if (musicInfo != null) {
                if (musicInfo.isFavourite()) {
                    mRemoteViews.setImageViewResource(R.id.img_fav, R.drawable.play_icn_loved);
                } else {
                    mRemoteViews.setImageViewResource(R.id.img_fav, R.drawable.play_icn_love);
                }
                updateToolsBar();
            }
        }
    }
}
