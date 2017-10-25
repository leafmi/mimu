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

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.activity.ArtistDetailActivity;
import musicdemo.jlang.com.mimu.adapter.ArtistAdapter;
import musicdemo.jlang.com.mimu.bean.Artist;
import musicdemo.jlang.com.mimu.dataloader.ArtistLoader;
import musicdemo.jlang.com.mimu.interfac.RecyclerViewOnItemClickListener;

/**
 * Created by JLang on 2017/10/16.
 */
public class ArtistsFragment extends Fragment implements RecyclerViewOnItemClickListener {
    private View root;
    private RecyclerView mArtistsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_artists, null);
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
        mArtistsRecyclerView = (RecyclerView) root.findViewById(R.id.artists_recycler_view);
    }


    private void initData() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mArtistsRecyclerView.setLayoutManager(mLayoutManager);

//        getAllArtists();
    }


    /**
     * 获取歌手的所有音乐（过滤了Artist为：<unknown>音乐）
     */
    private void getAllArtists() {
        ArtistLoader.getAllArtists(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Artist>>() {
                    @Override
                    public void accept(@NonNull List<Artist> artists) throws Exception {
                        ArtistAdapter artistAdapter = new ArtistAdapter(getActivity(), artists);
                        artistAdapter.setOnItemClickListener(ArtistsFragment.this);
                        mArtistsRecyclerView.setAdapter(artistAdapter);
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        Artist artist = (Artist) object;
        Intent intent = new Intent(getActivity(), ArtistDetailActivity.class);
        intent.putExtra("artist_id", artist.id);
        intent.putExtra("artist_name", artist.name);
        getActivity().startActivity(intent);
    }
}
