package com.synaric.app.mata.module.local;

import android.view.View;

import com.synaric.app.mata.R;
import com.synaric.app.mata.base.BaseFragment;

/**
 * 本地歌曲界面。
 * <br/><br/>Created by Synaric on 2016/10/21 0021.
 */
public class LocalAudioFragment extends BaseFragment {

    public static LocalAudioFragment newInstance() {
        return new LocalAudioFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_local_audio;
    }

    @Override
    protected void initToolBar(View root) {
        super.initToolBar(root);

        toolbar.setTitle(R.string.local_title);
    }
}
