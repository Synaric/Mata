package com.synaric.app.mata.module.main.my.local.artist;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpFragment;
import com.synaric.app.mata.module.main.my.local.LocalAudiosPresenter;
import com.synaric.app.widget.CompoundRecyclerView;
import com.synaric.app.widget.adapter.CommonAdapter;
import com.synaric.app.widget.adapter.CommonViewHolder;
import com.synaric.common.entity.ArtistInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 本地歌曲-艺人界面。
 * <br/><br/>Created by Synaric on 2016/10/26 0026.
 */
public class ArtistFragment extends MvpFragment<LocalAudiosPresenter>
        implements com.synaric.mvp.View<List<ArtistInfo>> {

    @InjectView(R.id.crv_container)
    CompoundRecyclerView crvContainer;

    private List<ArtistInfo> artistInfo = new ArrayList<>();

    public static ArtistFragment newInstance() {
        return new ArtistFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_list;
    }


    @Override
    protected LocalAudiosPresenter createPresenter() {
        return new LocalAudiosPresenter();
    }

    @Override
    protected void onCreateView(View root) {
        super.onCreateView(root);
        crvContainer.setAdapter(
            new CommonAdapter<ArtistInfo>(getContext(), artistInfo, R.layout.item_artist_info) {
                @Override
                protected void onBindViewHolder(CommonViewHolder holder, ArtistInfo entity) {
                    holder.setText(R.id.tv_artist, entity.getArtist());
                    holder.setFormatText(R.id.tv_count, R.string.local_file_count, entity.getTracks());
                }
        });
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        presenter.scanLocalArtists(this, getContext());
    }

    @Override
    public void onSuccess(List<ArtistInfo> data) {
        Logger.d("读取艺术家成功：" + data.size());
        crvContainer.notifyDataSetChanged(artistInfo, data, true, ArtistInfo::getArtistKey);
    }

    @Override
    public void onFailed(String error) {
        Logger.e(error);
        crvContainer.notifyDataSetChanged(artistInfo, null, true, ArtistInfo::getArtistKey);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onComplete() {

    }
}
