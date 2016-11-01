package com.synaric.app.mata.module.local.audio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpFragment;
import com.synaric.app.mata.module.local.LocalAudiosPresenter;
import com.synaric.app.widget.CompoundRecyclerView;
import com.synaric.app.widget.ViewUtils;
import com.synaric.app.widget.adapter.CommonAdapter;
import com.synaric.app.widget.adapter.CommonViewHolder;
import com.synaric.common.entity.AudioInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 本地歌曲-歌曲界面。
 * <br/><br/>Created by Synaric on 2016/10/26 0026.
 */
public class AudioFragment extends MvpFragment<LocalAudiosPresenter>
        implements com.synaric.mvp.View<List<AudioInfo>> {

    @InjectView(R.id.tv_random_play)
    TextView tvRandomPlay;
    @InjectView(R.id.crv_container)
    CompoundRecyclerView crvContainer;


    private List<AudioInfo> audioInfo = new ArrayList<>();

    public static AudioFragment newInstance() {
        return new AudioFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_local_audio;
    }

    @Override
    protected void onCreateView(View root) {
        super.onCreateView(root);
        crvContainer.setAdapter(new CommonAdapter<AudioInfo>(getContext(), audioInfo, R.layout.item_audio_info) {
            @Override
            public void onBindViewHolder(CommonViewHolder holder, int position) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //适配icon大小
        ViewUtils.resizeDrawableLeft(
                getContext(), tvRandomPlay, R.drawable.list_btn_orange_randomplay, 14
        );

        presenter.loadLocalAudios(this);
    }

    @OnClick(R.id.tv_random_play)
    public void onClick() {

    }

    @Override
    protected LocalAudiosPresenter createPresenter() {
        return new LocalAudiosPresenter();
    }

    @Override
    public void onSuccess(List<AudioInfo> data) {
        Logger.d("读取本地歌曲成功：" + data.size());
        crvContainer.notifyDataSetChanged(
                audioInfo,
                data,
                (oldItem, newItem) -> Objects.equals(oldItem.getId(), newItem.getId()));
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
}
