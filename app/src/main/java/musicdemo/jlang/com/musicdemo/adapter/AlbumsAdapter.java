package musicdemo.jlang.com.musicdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import musicdemo.jlang.com.musicdemo.R;
import musicdemo.jlang.com.musicdemo.bean.Album;
import musicdemo.jlang.com.musicdemo.fragment.RecyclerViewOnItemClickListener;
import musicdemo.jlang.com.musicdemo.util.ListenerUtil;
import musicdemo.jlang.com.musicdemo.util.NavigationUtil;

/**
 * Created by JLang on 2017/10/16.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerViewOnItemClickListener mItemClickListener;


    private List<Album> mAlbumsDataList;

    public AlbumsAdapter(Context mContext, List<Album> mAlbumsDataList) {
        this.mContext = mContext;
        this.mAlbumsDataList = mAlbumsDataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlbumsAdapter.ViewHolder viewHolder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_albums, parent, false);
        viewHolder = new ViewHolder(v, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Album album = mAlbumsDataList.get(position);
        holder.musicName.setText(album.title);
        holder.musicDes.setText(album.artistName + " - " + album.songCount + "首歌");

//        Glide.with(holder.itemView.getContext())
//                .load(ListenerUtil.getAlbumArtUri(album.id).toString())
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        super.onLoadFailed(e, errorDrawable);
//                        //TODO 加载出错的时处理
//                        holder.footer.setBackgroundColor(mContext.getResources().getColor(R.color.themeColor));
//                        holder.albumArt.setImageDrawable(ATEUtil.getDefaultAlbumDrawable(mContext));
//                        holder.musicName.setTextColor(mContext.getResources().getColor(R.color.white));
//                        holder.musicDes.setTextColor(mContext.getResources().getColor(R.color.white));
//                    }
//
//                    @Override
//                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        new Palette.Builder(resource).generate(new Palette.PaletteAsyncListener() {
//                            @Override
//                            public void onGenerated(Palette palette) {
//                                Palette.Swatch swatch = ColorUtil.getMostPopulousSwatch(palette);
//                                if (swatch != null) {
//                                    int color = swatch.getRgb();
//                                    holder.footer.setBackgroundColor(color);
//                                    int detailColor = swatch.getTitleTextColor();
//                                    holder.albumArt.setImageBitmap(resource);
//                                    holder.musicName.setTextColor(ColorUtil.getOpaqueColor(detailColor));
//                                    holder.musicDes.setTextColor(detailColor);
//                                }
//                            }
//                        });
//                    }
//                });
//
        if (ListenerUtil.isLollipop())
            holder.albumArt.setTransitionName("transition_album_art" + position);
    }

    @Override
    public int getItemCount() {
        return (null != mAlbumsDataList ? mAlbumsDataList.size() : 0);
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
        private View footer;
        private RecyclerViewOnItemClickListener mItemClickListener;


        ViewHolder(View itemView, RecyclerViewOnItemClickListener mItemClickListener) {
            super(itemView);
            this.mItemClickListener = mItemClickListener;
            this.musicName = (TextView) itemView.findViewById(R.id.tv_music_name);
            this.musicDes = (TextView) itemView.findViewById(R.id.tv_music_des);
            this.albumArt = (ImageView) itemView.findViewById(R.id.img_album);
            this.footer = itemView.findViewById(R.id.footer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition(), mAlbumsDataList.get(getAdapterPosition()));
            }
        }

    }
}
