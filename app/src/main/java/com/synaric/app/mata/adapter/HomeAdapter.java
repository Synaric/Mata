package com.synaric.app.mata.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.synaric.app.mata.R;
import com.synaric.app.mata.module.main.find.FindFragment;
import com.synaric.app.mata.module.main.musicroom.MusicRoomFragment;
import com.synaric.app.mata.module.main.my.MyFragment;

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
        switch (position) {
            case 0:
                return MyFragment.newInstance();
            case 1:
                return MusicRoomFragment.newInstance();
            case 2:
                return FindFragment.newInstance();
            default:
                throw new IllegalArgumentException("position must <= 2");
        }
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
