package com.synaric.app.mata.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.synaric.app.widget.ViewUtil;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public abstract class BaseFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View root;
        if(layoutId <= 0) {
            root = ViewUtil.createDescriptionView(getContext(), getClass().getSimpleName());
        } else {
            root = inflater.inflate(layoutId, container, false);
        }
        ButterKnife.inject(this, root);
        onCreateView(root);
        return root;
    }

    protected void onCreateView(View root) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    protected void screenTransitAnim(View view, int targetId, Intent intent) {
        ((BaseActivity) getContext()).screenTransitAnim(view, targetId, intent);
    }

    public abstract int getLayoutId();
}
