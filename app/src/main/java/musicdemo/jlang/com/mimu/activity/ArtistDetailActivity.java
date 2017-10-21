package musicdemo.jlang.com.mimu.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.adapter.SongsAdapter;
import musicdemo.jlang.com.mimu.bean.ArtistArt;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.dataloader.ArtistSongLoader;
import musicdemo.jlang.com.mimu.util.ATEUtil;
import musicdemo.jlang.com.mimu.util.ColorUtil;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

public class ArtistDetailActivity extends AppCompatActivity {
    private ImageView mAlbumArt;
    private RecyclerView mSongsRecyclerView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private long artistId;
    private String artistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        initView();
        initData();
    }

    private void initView() {
        mAlbumArt = (ImageView) findViewById(R.id.album_art);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mSongsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_songs);
    }

    private void initData() {
        artistId = getIntent().getLongExtra("artist_id", -1);
        artistName = getIntent().getStringExtra("artist_name");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSongsRecyclerView.setLayoutManager(mLayoutManager);

        setupToolbar();
        loadAlbumArt();
        getSongsByArtistId();
    }

    private void setupToolbar() {
        mCollapsingToolbarLayout.setTitle(artistName);
    }

    private void loadAlbumArt() {
        String artistArtJson = PreferencesUtility.getInstance(this).getArtistArt(artistId);
        if (!TextUtils.isEmpty(artistArtJson)) {
            ArtistArt artistArt = new Gson().fromJson(artistArtJson, ArtistArt.class);
            Glide.with(this)
                    .load(artistArt.getExtralarge())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .priority(Priority.IMMEDIATE)
                    .error(ATEUtil.getDefaultArtistDrawable(this))
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
    }

    private void getSongsByArtistId() {
        ArtistSongLoader.getSongsForArtist(this, artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MusicInfo>>() {
                    @Override
                    public void accept(List<MusicInfo> songs) throws Exception {
                        SongsAdapter songsAdapter = new SongsAdapter(ArtistDetailActivity.this, songs);
                        mSongsRecyclerView.setAdapter(songsAdapter);
                    }
                });
    }
}
