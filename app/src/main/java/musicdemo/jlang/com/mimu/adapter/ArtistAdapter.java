package musicdemo.jlang.com.mimu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.bean.Artist;
import musicdemo.jlang.com.mimu.bean.ArtistArt;
import musicdemo.jlang.com.mimu.interfac.RecyclerViewOnItemClickListener;
import musicdemo.jlang.com.mimu.retrofit.RetrofitHelper;
import musicdemo.jlang.com.mimu.retrofit.bean.ArtistInfo;
import musicdemo.jlang.com.mimu.retrofit.bean.Artwork;
import musicdemo.jlang.com.mimu.util.ATEUtil;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/16.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private Context mContext;
    private List<Artist> mArtistsDataList;
    private RecyclerViewOnItemClickListener mItemClickListener;

    public ArtistAdapter(Context mContext, List<Artist> mArtistsList) {
        this.mContext = mContext;
        this.mArtistsDataList = mArtistsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArtistAdapter.ViewHolder viewHolder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_songs, parent, false);
        viewHolder = new ArtistAdapter.ViewHolder(v, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Artist artist = mArtistsDataList.get(position);

        holder.musicName.setText(artist.name);
        holder.musicDes.setText(artist.albumCount + "张专辑" + " | " + artist.songCount + "首歌");

        String artistArtJson = PreferencesUtility.getInstance(mContext).getArtistArt(artist.id);
        if (TextUtils.isEmpty(artistArtJson)) {
            RetrofitHelper.getLastFmApiService().getArtistInfo(artist.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(new Function<Throwable, ArtistInfo>() {
                        @Override
                        public ArtistInfo apply(@NonNull Throwable throwable) throws Exception {
                            Toast.makeText(mContext, "load error", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                    })
                    .subscribe(new Consumer<ArtistInfo>() {
                        @Override
                        public void accept(ArtistInfo artistInfo) throws Exception {
                            if (artistInfo != null && artistInfo.mArtist != null && artistInfo.mArtist.mArtwork != null) {
                                List<Artwork> artworks = artistInfo.mArtist.mArtwork;
                                ArtistArt artistArt = new ArtistArt(artworks.get(0).mUrl, artworks.get(1).mUrl,
                                        artworks.get(2).mUrl, artworks.get(3).mUrl);
                                PreferencesUtility.getInstance(mContext).setArtistArt(artist.id, new Gson().toJson(artistArt));

                                Glide.with(mContext)
                                        .load(artistArt.getLarge())
                                        .placeholder(ATEUtil.getDefaultArtistDrawable(mContext))
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .error(ATEUtil.getDefaultArtistDrawable(mContext))
                                        .into(holder.albumArt);
                            }
                        }
                    });
        } else {
            ArtistArt artistArt = new Gson().fromJson(artistArtJson, ArtistArt.class);
            Glide.with(mContext)
                    .load(artistArt.getLarge())
                    .placeholder(ATEUtil.getDefaultArtistDrawable(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(ATEUtil.getDefaultArtistDrawable(mContext))
                    .into(holder.albumArt);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mArtistsDataList ? mArtistsDataList.size() : 0);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(RecyclerViewOnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView musicName;
        private TextView musicDes;
        private ImageView albumArt;
        private RecyclerViewOnItemClickListener mItemClickListener;


        ViewHolder(View itemView, RecyclerViewOnItemClickListener mItemClickListener) {
            super(itemView);
            this.mItemClickListener = mItemClickListener;
            this.musicName = (TextView) itemView.findViewById(R.id.tv_music_name);
            this.musicDes = (TextView) itemView.findViewById(R.id.tv_music_des);
            this.albumArt = (ImageView) itemView.findViewById(R.id.img_album);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition(), mArtistsDataList.get(getAdapterPosition()));
            }
        }
    }
}
