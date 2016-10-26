package com.synaric.app.mata.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.synaric.app.mata.R;
import com.synaric.app.mata.module.local.AlbumFragment;
import com.synaric.app.mata.module.local.ArtistFragment;
import com.synaric.app.mata.module.local.AudioFragment;
import com.synaric.app.mata.module.local.CompilationFragment;
import com.synaric.app.mata.module.local.FolderFragment;

/**
 * <br/><br/>Created by Synaric on 2016/10/26 0026.
 */
public class LocalAudioAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;

    public LocalAudioAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getApplicationContext().getResources().getStringArray(R.array.local_tabs);
    }

    @Override
    public Fragment getItem(int position) {
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

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
