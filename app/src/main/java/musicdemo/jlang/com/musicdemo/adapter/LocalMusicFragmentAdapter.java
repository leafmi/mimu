package musicdemo.jlang.com.musicdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JLang on 2017/10/16.
 */

public class LocalMusicFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>(4);
    private List<String> mTitleList = new ArrayList<>(4);

    public LocalMusicFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> mTitleList) {
        super(fm);
        this.fragments = fragments;
        this.mTitleList = mTitleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
