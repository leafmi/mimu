package musicdemo.jlang.com.mimu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.interfac.RecyclerViewOnItemClickListener;
import musicdemo.jlang.com.mimu.util.ATEUtil;
import musicdemo.jlang.com.mimu.util.ListenerUtil;

/**
 * Created by JLang on 2017/10/16.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private Context mContext;
    private List<MusicInfo> mSongsDataList;
    private RecyclerViewOnItemClickListener mItemClickListener;


    public SongsAdapter(Context mContext, List<MusicInfo> mSongsDataList) {
        this.mContext = mContext;
        this.mSongsDataList = mSongsDataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SongsAdapter.ViewHolder viewHolder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_songs, parent, false);
        viewHolder = new ViewHolder(v, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MusicInfo song = mSongsDataList.get(position);
        holder.musicName.setText(song.title);
        holder.musicDes.setText(song.artistName + " - " + song.albumName);

        Glide.with(holder.itemView.getContext()).load(ListenerUtil.getAlbumArtUri(song.albumId).toString())
                .error(ATEUtil.getDefaultAlbumDrawable(mContext))
                .placeholder(ATEUtil.getDefaultAlbumDrawable(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(holder.albumArt);
    }

    @Override
    public int getItemCount() {
        return (null != mSongsDataList ? mSongsDataList.size() : 0);
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

        ViewHolder(View itemView, RecyclerViewOnItemClickListener listener) {
            super(itemView);
            this.mItemClickListener = listener;
            this.musicName = (TextView) itemView.findViewById(R.id.tv_music_name);
            this.musicDes = (TextView) itemView.findViewById(R.id.tv_music_des);
            this.albumArt = (ImageView) itemView.findViewById(R.id.img_album);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition(), mSongsDataList);
            }
        }
    }
}
