package musicdemo.jlang.com.mimu.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.activity.FolderDetailActivity;
import musicdemo.jlang.com.mimu.adapter.FoldersAdapter;
import musicdemo.jlang.com.mimu.bean.FolderInfo;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.dataloader.FolderLoader;
import musicdemo.jlang.com.mimu.dataloader.SongLoader;

/**
 * Created by JLang on 2017/10/16.
 */

public class FoldersFragment extends Fragment implements RecyclerViewOnItemClickListener {
    private View root;
    private RecyclerView mFoldersRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_folders, null);
        }
        initView();
        initData();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        mFoldersRecyclerView = (RecyclerView) root.findViewById(R.id.folders_recycler_view);
    }

    private void initData() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mFoldersRecyclerView.setLayoutManager(mLayoutManager);
        getAllMusicFolders();
    }

    /**
     * 获取所有音乐文件的文件夹
     */
    private void getAllMusicFolders() {
        FolderLoader.getFoldersWithSong(getActivity())
                .map(new Function<List<FolderInfo>, List<FolderInfo>>() {
                    @Override
                    public List<FolderInfo> apply(@NonNull List<FolderInfo> folderInfos) throws Exception {
                        final List<FolderInfo> maps = new ArrayList();
                        for (int i = 0; i < folderInfos.size(); i++) {
                            final FolderInfo folderInfo = folderInfos.get(i);
                            String folderPath = folderInfo.folderPath;
                            SongLoader.getSongListInFolder(getActivity(), folderPath)
                                    .subscribe(new Consumer<List<MusicInfo>>() {
                                        @Override
                                        public void accept(List<MusicInfo> songs) throws Exception {
                                            if (songs.size() > 0) {
                                                maps.add(folderInfo);
                                            }
                                        }
                                    });
                        }
                        return maps;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FolderInfo>>() {
                    @Override
                    public void accept(@NonNull List<FolderInfo> folderInfos) throws Exception {
                        FoldersAdapter foldersAdapter = new FoldersAdapter(getActivity(), folderInfos);
                        foldersAdapter.setOnItemClickListener(FoldersFragment.this);
                        mFoldersRecyclerView.setAdapter(foldersAdapter);
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        FolderInfo folderInfo = (FolderInfo) object;
        Intent intent = new Intent(getActivity(), FolderDetailActivity.class);
        intent.putExtra("folder_path", folderInfo.folderPath);
        getActivity().startActivity(intent);
    }
}
