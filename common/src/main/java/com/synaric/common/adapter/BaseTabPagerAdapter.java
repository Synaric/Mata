package com.synaric.common.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.synaric.mvp.Presenter;
import com.synaric.mvp.View;

/**
 * <br/><br/>Created by Synaric on 2016/10/27 0027.
 */
public abstract class BaseTabPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;

    private Presenter<View>  presenter;

    public BaseTabPagerAdapter(FragmentManager fm, Context context, int stringArrayId, Presenter<View> presenter) {
        super(fm);
        TITLES = context.getApplicationContext().getResources().getStringArray(stringArrayId);
        this.presenter = presenter;
    }

    @Override
    public final Fragment getItem(int position) {
        Fragment fragment = getItemInternal(position);
        if(fragment != null && fragment instanceof View) presenter.attachView((View) fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    public abstract Fragment getItemInternal(int position);
}
