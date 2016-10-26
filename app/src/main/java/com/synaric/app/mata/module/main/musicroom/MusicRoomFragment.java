package com.synaric.app.mata.module.main.musicroom;

import com.synaric.app.mata.base.BaseManagedFragment;

/**
 * "音乐馆"界面。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public class MusicRoomFragment extends BaseManagedFragment {

    public static MusicRoomFragment newInstance() {
        return new MusicRoomFragment();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setEnableSwipeBack(false);
    }
}
