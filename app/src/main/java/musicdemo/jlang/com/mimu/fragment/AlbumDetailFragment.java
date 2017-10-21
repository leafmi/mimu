package musicdemo.jlang.com.mimu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import musicdemo.jlang.com.mimu.R;
import musicdemo.jlang.com.mimu.util.Constants;

/**
 * Created by JLang on 2017/10/17.
 */

public class AlbumDetailFragment extends Fragment {
    private View root;

    public static AlbumDetailFragment newInstance(long id, String name, boolean useTransition, String transitionName) {
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        Bundle args = new Bundle();
        args.putLong(Constants.ALBUM_ID, id);
        args.putString(Constants.ALBUM_NAME, name);
        args.putBoolean("transition", useTransition);
        if (useTransition)
            args.putString("transition_name", transitionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root != null) {
            root = inflater.inflate(R.layout.fragment_album_detatil, null);
        }
        return root;
    }
}
