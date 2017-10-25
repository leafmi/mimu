package musicdemo.jlang.com.mimu.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.bean.MusicPlayInfo;
import musicdemo.jlang.com.mimu.event.message.EventCloseMusicPlayModule;
import musicdemo.jlang.com.mimu.manager.MusicPlayInfoManager;
import musicdemo.jlang.com.mimu.manager.MusicPlayerManager;
import musicdemo.jlang.com.mimu.util.music.MusicAction;
import musicdemo.jlang.com.mimu.util.music.MusicPlaySate;


/**
 * Created by wm on 2016/2/4.
 */
public class PlayQueueFragment extends AttachDialogFragment {


    private TextView playlistNumber, clearAll, addToPlaylist;
    private int currentlyPlayingPosition = 0;
    private RecyclerView mRecyclerView;  //弹出的activity列表

    private List<MusicPlayInfo> musicPlayListData;
    private PlayingListAdapter playingListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setAttributes(params);
        }

        View view = inflater.inflate(R.layout.fragment_play_queue, container);

        //布局
        playlistNumber = (TextView) view.findViewById(R.id.play_list_number);
        clearAll = (TextView) view.findViewById(R.id.playlist_clear_all);


        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllPlayQueue(true);
                if (playingListAdapter != null)
                    playingListAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_play_queue);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        new loadSongs().execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.6);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);

    }


    public void onPlayingListItemClick(View view, int position, MusicPlayInfo musicPlayInfo) {
        MusicPlayInfoManager.getInstance().setCurrentPlayingIndex(position);
        MusicPlayerManager.getInstance(mContext).playAction(musicPlayInfo, musicPlayInfo.getType());
    }

    //异步加载recyclerView界面
    private class loadSongs extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            MusicPlayInfoManager musicPlayInfoManager = MusicPlayInfoManager.getInstance();
            musicPlayListData = musicPlayInfoManager.getMusicPlayListData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (musicPlayListData != null && musicPlayListData.size() > 0) {
                int size = musicPlayListData.size();
                playingListAdapter = new PlayingListAdapter(mContext, musicPlayListData);
                mRecyclerView.setAdapter(playingListAdapter);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                playlistNumber.setText("播放列表（" + size + "）");
                //滑动到当前播放Item;
                MusicPlayInfo playMusicInfo = MusicPlayInfoManager.getInstance().getPlayMusicInfo();
                if (playMusicInfo != null) {
                    for (int i = 0; i < size; i++) {
                        MusicPlayInfo musicPlayInfo = musicPlayListData.get(i);
                        if (musicPlayInfo != null && musicPlayInfo.getData().equals(playMusicInfo.getData())) {
                            if (i > 3) {
                                mRecyclerView.scrollToPosition(i - 3);
                            } else {
                                mRecyclerView.scrollToPosition(i);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 清空播放列表：停止播放，清空数据，关闭播放详情页
     */
    private void clearAllPlayQueue(boolean isNeedClearData) {
        //停止播放
        MusicPlayerManager.getInstance(mContext).playAction(MusicPlaySate.PLAYING);
        if (isNeedClearData) {
            //清除数据
            MusicPlayInfoManager.getInstance().clearCurrentPlayListAndDB();
        }
        //发送消息关闭播放详情页和首页播放模块
        EventBus.getDefault().post(new EventCloseMusicPlayModule());
    }

    //*************PlayingListAdapter***********
    class PlayingListAdapter extends RecyclerView.Adapter<PlayingListAdapter.ViewHolder> {

        private Context mContext;
        private List<MusicPlayInfo> musicPlayInfos;


        PlayingListAdapter(Context mContext, List<MusicPlayInfo> musicPlayInfos) {
            this.mContext = mContext;
            this.musicPlayInfos = musicPlayInfos;
        }

        public void updateDataSet(ArrayList<MusicPlayInfo> musicPlayInfos) {
            this.musicPlayInfos = musicPlayInfos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_playqueue_item, parent, false));
        }

        @Override
        public void onBindViewHolder(PlayingListAdapter.ViewHolder holder, int position) {
            MusicPlayInfo musicPlayInfo = musicPlayInfos.get(position);
            holder.title.setText(musicPlayInfo.getTitle());
            holder.artist.setText(musicPlayInfo.getArtistName());
        }

        @Override
        public int getItemCount() {
            return (null != musicPlayInfos ? musicPlayInfos.size() : 0);
        }


        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ImageView delete;
            private TextView title, artist;


            ViewHolder(View itemView) {
                super(itemView);
                this.title = (TextView) itemView.findViewById(R.id.play_list_title);
                this.artist = (TextView) itemView.findViewById(R.id.play_list_artist);
                this.delete = (ImageView) itemView.findViewById(R.id.play_list_delete);

                itemView.setOnClickListener(this);
                this.delete.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.play_list_delete:
                        MusicPlayInfo musicPlayInfo = musicPlayInfos.get(getAdapterPosition());
                        int adapterPosition = getAdapterPosition();
                        boolean musicPlaying = MusicPlayerManager.getInstance(mContext).isMusicPlaying();
                        MusicPlayInfoManager musicPlayInfoManager = MusicPlayInfoManager.getInstance();
                        MusicPlayInfo playMusicInfo = musicPlayInfoManager.getPlayMusicInfo();
                        //删除该条列表信息
                        musicPlayInfoManager.clearCurrentPlayListAndDB(adapterPosition);
                        notifyDataSetChanged();
                        int size = musicPlayInfos.size();
                        if (size > 0) {
                            if (musicPlaying && playMusicInfo != null) {
                                if (playMusicInfo.getData().equals(musicPlayInfo.getData())) {
                                    if (adapterPosition == size) {
                                        //切换上一曲
                                        onPlayingListItemClick(view, adapterPosition - 1, musicPlayInfos.get(adapterPosition - 1));
                                    } else {
                                        //切换下一曲
                                        onPlayingListItemClick(view, adapterPosition, musicPlayInfos.get(adapterPosition));
                                    }
                                }
                            }
                            //刷新列表Size
                            playlistNumber.setText("播放列表（" + size + "）");
                        } else {
                            //停止播放，关闭播放列表
                            clearAllPlayQueue(false);
                            dismiss();
                        }
                        break;
                    default:
                        onPlayingListItemClick(view, getAdapterPosition(), musicPlayInfos.get(getAdapterPosition()));
                        break;
                }
            }
        }

    }
}
