package com.synaric.app.mata.module.main.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synaric.app.mata.R;
import com.synaric.app.mata.base.BaseFragment;
import com.synaric.app.mata.module.local.LocalAudioFragment;
import com.synaric.app.mata.module.main.root.HomeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * "我的"界面。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public class MyFragment extends BaseFragment implements MyView<String> {

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_my;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setEnableSwipeBack(false);
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

    @OnClick({R.id.ll_local_audio})
    public void onClick(View view) {
        final int id = view.getId();

        switch (id) {
            case R.id.ll_local_audio:
                syncStartFragment(HomeFragment.class, LocalAudioFragment.newInstance(), SupportFragment.STANDARD);
                break;
        }
    }
}
