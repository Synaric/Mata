package com.synaric.app.mata.module.main.root;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.synaric.app.mata.R;
import com.synaric.app.mata.adapter.HomeAdapter;
import com.synaric.app.mata.base.BaseFragment;
import com.synaric.app.mata.event.RequestToggleDrawer;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主界面，这个Fragment将被设置为根Fragment,用于单Activity + 多Fragment模式。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 *
 * @see SupportActivity#loadRootFragment(int, SupportFragment)
 */
public class HomeFragment extends BaseFragment {

    @InjectView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabs;
    @InjectView(R.id.vp_container)
    ViewPager viewPager;
    @InjectView(R.id.civ_head_portrait)
    CircleImageView civHeadPortrait;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setEnableSwipeBack(false);
    }

    @Override
    protected void onCreateView(View root) {
        super.onCreateView(root);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new HomeAdapter(getFragmentManager(), getContext()));
        slidingTabs.setViewPager(viewPager);

        civHeadPortrait.setOnClickListener(v -> eventBus.post(RequestToggleDrawer.get()));
    }
}
