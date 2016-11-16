package com.synaric.app.mata.event;

import com.synaric.app.mata.base.BaseFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 异步请求开启Fragment。
 * 一些难以通过{@link BaseFragment#startFragment(BaseFragment, int)}
 * 或者{@link me.yokeyword.fragmentation.SupportFragment#start(SupportFragment, int)}来启动新
 * Fragment的情况下（例如ViewPager内的Fragment想要打开新Fragment），需要通过本事件请求。
 * {@link com.synaric.app.mata.module.main.root.HomeFragment}处理事件。
 * <br/><br/>Created by Synaric on 2016/10/21 0021.
 */
public class RequestStartFragment {

    public Class<? extends SupportFragment> from;
    public SupportFragment to;
    public int launchMode;

    public RequestStartFragment(Class<? extends BaseFragment> from, SupportFragment to, int launchMode) {
        this.from = from;
        this.to = to;
        this.launchMode = launchMode;
    }

    public RequestStartFragment(Class<? extends BaseFragment> from, SupportFragment to) {
        this(from, to, SupportFragment.STANDARD);
    }
}
