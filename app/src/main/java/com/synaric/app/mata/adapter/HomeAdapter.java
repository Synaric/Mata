package com.synaric.app.mata.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.synaric.app.mata.R;

/**
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public class HomeAdapter extends FragmentPagerAdapter{

    private final String[] TITLES;

    public HomeAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getApplicationContext().getResources().getStringArray(R.array.main_tabs);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
