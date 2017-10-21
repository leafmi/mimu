package musicdemo.jlang.com.musicdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.musicdemo.R;
import musicdemo.jlang.com.musicdemo.adapter.SongsAdapter;
import musicdemo.jlang.com.musicdemo.bean.MusicInfo;
import musicdemo.jlang.com.musicdemo.dataloader.SongLoader;

public class FolderDetailActivity extends AppCompatActivity {

    private RecyclerView mFolderRecyclerView;

    private String folderPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);
        initView();
        initData();
    }

    private void initView() {
        mFolderRecyclerView = (RecyclerView) findViewById(R.id.folder_recycler_view);
    }

    private void initData() {
        //获取Music Path
        folderPath = getIntent().getStringExtra("folder_path");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mFolderRecyclerView.setLayoutManager(mLayoutManager);

        getSongsByFolderPath();
    }

    private void getSongsByFolderPath() {
        SongLoader.getSongListInFolder(this, folderPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MusicInfo>>() {
                    @Override
                    public void accept(List<MusicInfo> songs) throws Exception {
                        SongsAdapter songsAdapter = new SongsAdapter(FolderDetailActivity.this, songs);
                        mFolderRecyclerView.setAdapter(songsAdapter);
                    }
                });
    }

}
