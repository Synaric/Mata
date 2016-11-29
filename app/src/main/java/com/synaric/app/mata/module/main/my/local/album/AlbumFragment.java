package com.synaric.app.mata.module.main.my.local.album;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpFragment;
import com.synaric.app.mata.module.main.my.local.LocalAudiosPresenter;
import com.synaric.app.widget.CompoundRecyclerView;
import com.synaric.app.widget.adapter.CommonAdapter;
import com.synaric.app.widget.adapter.CommonViewHolder;
import com.synaric.common.entity.AlbumInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 本地歌曲-专辑界面。
 * <br/><br/>Created by Synaric on 2016/10/26 0026.
 */
public class AlbumFragment extends MvpFragment<LocalAudiosPresenter>
        implements com.synaric.mvp.View<List<AlbumInfo>> {

    @InjectView(R.id.crv_container)
    CompoundRecyclerView crvContainer;

    private List<AlbumInfo> albumInfo = new ArrayList<>();

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    protected LocalAudiosPresenter createPresenter() {
        return new LocalAudiosPresenter();
    }

    @Override
    protected void onCreateView(View root) {
        super.onCreateView(root);
        crvContainer.setAdapter(
            new CommonAdapter<AlbumInfo>(getContext(), albumInfo, R.layout.item_album_info) {
                @Override
                protected void onBindViewHolder(CommonViewHolder holder, AlbumInfo entity) {
                    holder.setImagePath(getContext(), R.id.iv_cover, entity.getAlbumArt(), 0);
                    holder.setText(R.id.tv_title, entity.getAlbum());
                    holder.setText(R.id.tv_artist, entity.getArtist());
                    holder.setFormatText(R.id.tv_count, R.string.local_file_count, entity.getSongs());
                }
        });
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        presenter.scanLocalAlbums(this, getContext());
    }

    @Override
    public void onSuccess(List<AlbumInfo> data) {
        Logger.d("读取专辑成功：" + data.size());
        crvContainer.notifyDataSetChanged(albumInfo, data, true, AlbumInfo::getAlbumKey);
    }

    @Override
    public void onFailed(String error) {
        crvContainer.notifyDataSetChanged(albumInfo, null, true, AlbumInfo::getAlbumKey);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onComplete() {

    }
}
