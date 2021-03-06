package com.synaric.app.mata.module.main.my.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.synaric.app.mata.R;
import com.synaric.app.mata.adapter.LocalAudioAdapter;
import com.synaric.app.mata.base.MvpFragment;

import butterknife.InjectView;

/**
 * 本地歌曲界面。
 * <br/><br/>Created by Synaric on 2016/10/21 0021.
 */
public class LocalAudiosFragment extends MvpFragment<LocalAudiosPresenter> {

    @InjectView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabs;
    @InjectView(R.id.vp_container)
    ViewPager viewPager;

    public static LocalAudiosFragment newInstance() {
        return new LocalAudiosFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_local_audios;
    }

    @Override
    protected void initToolBar(View root) {
        super.initToolBar(root);
        toolbar.setTitle(R.string.local_title);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new LocalAudioAdapter(getChildFragmentManager(), getContext(), presenter));
        slidingTabs.setViewPager(viewPager);
    }

    @Override
    protected LocalAudiosPresenter createPresenter() {
        return new LocalAudiosPresenter();
    }
}
