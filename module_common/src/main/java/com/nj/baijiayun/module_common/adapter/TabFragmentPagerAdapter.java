package com.nj.baijiayun.module_common.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019/4/28
 * @describe
 */
public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;
    private List<String> titlesArray;
    private List<Fragment> fragments;

    public TabFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titlesArray) {
        super(fm);
        this.fragments = fragments;
        this.titlesArray = titlesArray;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (titles != null && titles.length > 0) {
            return titles[position];
        }
        if (titlesArray != null && titlesArray.size() > 0) {
            return titlesArray.get(position);
        }
        return "";
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}

