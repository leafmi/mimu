package musicdemo.jlang.com.musicdemo.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import musicdemo.jlang.com.musicdemo.R;
import musicdemo.jlang.com.musicdemo.bean.FolderInfo;
import musicdemo.jlang.com.musicdemo.fragment.RecyclerViewOnItemClickListener;

/**
 * Created by JLang on 2017/10/17.
 */

public class FoldersAdapter extends RecyclerView.Adapter<FoldersAdapter.ViewHolder> {
    private Context mContext;
    private List<FolderInfo> mFolderInfoDataList;
    private RecyclerViewOnItemClickListener mItemClickListener;


    public FoldersAdapter(Context mContext, List<FolderInfo> mFolderInfoDataList) {
        this.mContext = mContext;
        this.mFolderInfoDataList = mFolderInfoDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FoldersAdapter.ViewHolder viewHolder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_folder, parent, false);
        viewHolder = new FoldersAdapter.ViewHolder(v, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FolderInfo folderInfo = mFolderInfoDataList.get(position);

        Drawable image = mContext.getResources().getDrawable(R.drawable.ic_folder_black_48dp);
        image.setColorFilter(mContext.getResources().getColor(R.color.folderTint), PorterDuff.Mode.SRC_IN);
        holder.imgFolder.setImageDrawable(image);

        holder.tvTitle.setText(folderInfo.folderName);
        holder.tvDes.setText(folderInfo.songCount + "首歌" + " | " + folderInfo.folderPath);
    }

    @Override
    public int getItemCount() {
        return (null != mFolderInfoDataList ? mFolderInfoDataList.size() : 0);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(RecyclerViewOnItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvDes;
        private ImageView imgFolder;
        private RecyclerViewOnItemClickListener mItemClickListener;

        public ViewHolder(View itemView, RecyclerViewOnItemClickListener mItemClickListener) {
            super(itemView);
            this.mItemClickListener = mItemClickListener;
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvDes = (TextView) itemView.findViewById(R.id.tv_des);
            this.imgFolder = (ImageView) itemView.findViewById(R.id.img_folder);

            //设置点击事件
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition(), mFolderInfoDataList.get(getAdapterPosition()));
            }
        }
    }
}
