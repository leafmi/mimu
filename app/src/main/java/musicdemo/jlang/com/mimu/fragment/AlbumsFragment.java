package musicdemo.jlang.com.mimu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.activity.AlbumDetailActivity;
import musicdemo.jlang.com.mimu.adapter.AlbumsAdapter;
import musicdemo.jlang.com.mimu.bean.Album;
import musicdemo.jlang.com.mimu.dataloader.AlbumLoader;

/**
 * Created by JLang on 2017/10/16.
 */
public class AlbumsFragment extends Fragment implements RecyclerViewOnItemClickListener {
    private View root;
    private RecyclerView mAlbumsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_albums, null);
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
        mAlbumsRecyclerView = (RecyclerView) root.findViewById(R.id.albums_recycler_view);
    }

    private void initData() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAlbumsRecyclerView.setLayoutManager(mLayoutManager);

        getAllAlbums();
    }

    /**
     * 获取所有专辑（过滤了Artist为：<unknown>音乐）
     */
    private void getAllAlbums() {
        AlbumLoader.getAllAlbums(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Album>>() {
                    @Override
                    public void accept(@NonNull List<Album> alba) throws Exception {
                        AlbumsAdapter albumsAdapter = new AlbumsAdapter(getActivity(), alba);
                        albumsAdapter.setOnItemClickListener(AlbumsFragment.this);
                        mAlbumsRecyclerView.setAdapter(albumsAdapter);
                    }
                });
    }


    @Override
    public void onItemClick(View view, int position, Object object) {
        Album album = (Album) object;
        Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
        intent.putExtra("album_id", album.id);
        intent.putExtra("album_name", album.title);
        getActivity().startActivity(intent);

    }
}
