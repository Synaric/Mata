package com.synaric.app.mata.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.synaric.app.mata.R;
import com.synaric.app.mata.module.local.album.AlbumFragment;
import com.synaric.app.mata.module.local.artist.ArtistFragment;
import com.synaric.app.mata.module.local.audio.AudioFragment;
import com.synaric.app.mata.module.local.compilation.CompilationFragment;
import com.synaric.app.mata.module.local.folder.FolderFragment;
import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.common.adapter.BaseTabPagerAdapter;

/**
 * <br/><br/>Created by Synaric on 2016/10/26 0026.
 */
public class LocalAudioAdapter extends BaseTabPagerAdapter {

    public LocalAudioAdapter(FragmentManager fm, Context context, BasePresenter presenter) {
        super(fm, context, R.array.local_tabs, presenter);
    }

    @Override
    public Fragment getItemInternal(int position) {
        switch (position) {
            case 0:
                return AudioFragment.newInstance();
            case 1:
                return ArtistFragment.newInstance();
            case 2:
                return AlbumFragment.newInstance();
            case 3:
                return CompilationFragment.newInstance();
            case 4:
                return FolderFragment.newInstance();
            default:
                throw new IllegalArgumentException("position must <= 4");
        }
    }
}
