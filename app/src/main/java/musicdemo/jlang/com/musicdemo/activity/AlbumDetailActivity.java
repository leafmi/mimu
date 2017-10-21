package musicdemo.jlang.com.musicdemo.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.musicdemo.R;
import musicdemo.jlang.com.musicdemo.adapter.SongsAdapter;
import musicdemo.jlang.com.musicdemo.bean.MusicInfo;
import musicdemo.jlang.com.musicdemo.dataloader.AlbumSongLoader;
import musicdemo.jlang.com.musicdemo.util.ATEUtil;
import musicdemo.jlang.com.musicdemo.util.ColorUtil;
import musicdemo.jlang.com.musicdemo.util.ListenerUtil;

public class AlbumDetailActivity extends AppCompatActivity {
    private ImageView mAlbumArt;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mSongsRecyclerView;

    private long albumId;
    private String albumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        initView();
        initData();

    }

    private void initView() {
        mAlbumArt = (ImageView) findViewById(R.id.album_art);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mSongsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_songs);
    }

    private void initData() {
        albumId = getIntent().getLongExtra("album_id", -1);
        albumName = getIntent().getStringExtra("album_name");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSongsRecyclerView.setLayoutManager(mLayoutManager);

        setupToolbar();
        loadAlbumArt();
        getSongsByAlbumId();
    }

    private void setupToolbar() {
        mCollapsingToolbarLayout.setTitle(albumName);
    }

    private void loadAlbumArt() {
        Glide.with(this)
                .load(ListenerUtil.getAlbumArtUri(albumId))
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .error(ATEUtil.getDefaultAlbumDrawable(this))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        mAlbumArt.setImageDrawable(errorDrawable);
                        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.themeColor));
                        mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorAccent));
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mAlbumArt.setImageBitmap(resource);
                        new Palette.Builder(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch swatch = ColorUtil.getMostPopulousSwatch(palette);
                                if (swatch != null) {
                                    int color = swatch.getRgb();
                                    mCollapsingToolbarLayout.setContentScrimColor(color);
                                    mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorAccent));
                                }
                            }
                        });
                    }
                });
    }

    private void getSongsByAlbumId() {
        AlbumSongLoader.getSongsForAlbum(this, albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MusicInfo>>() {
                    @Override
                    public void accept(List<MusicInfo> songs) throws Exception {
                        SongsAdapter songsAdapter = new SongsAdapter(AlbumDetailActivity.this, songs);
                        mSongsRecyclerView.setAdapter(songsAdapter);
                    }
                });
    }
}
