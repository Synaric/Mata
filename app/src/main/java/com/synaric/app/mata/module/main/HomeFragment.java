package com.synaric.app.mata.module.main;

import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpFragment;

import butterknife.InjectView;

/**
 * "我的"界面。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public class HomeFragment extends MvpFragment<HomePresenter> implements HomeView<String> {

    @InjectView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabs;
    @InjectView(R.id.vp_container)
    ViewPager viewPager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void onFailed(String error) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onComplete() {

    }
}
