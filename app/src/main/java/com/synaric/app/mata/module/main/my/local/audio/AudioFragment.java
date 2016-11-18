package com.synaric.app.mata.module.main.my.local.audio;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpFragment;
import com.synaric.app.mata.module.main.my.local.LocalAudiosPresenter;
import com.synaric.app.player.PlayerService;
import com.synaric.app.widget.CompoundRecyclerView;
import com.synaric.app.widget.ViewUtils;
import com.synaric.app.widget.adapter.CommonAdapter;
import com.synaric.app.widget.adapter.CommonViewHolder;
import com.synaric.common.entity.AudioInfo;
import com.synaric.common.utils.AudioInfoUtils;

import java.util.ArrayList;
import java.util.List;

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
    protected LocalAudiosPresenter createPresenter() {
        return new LocalAudiosPresenter();
    }

    @Override
    protected void onCreateView(View root) {
        super.onCreateView(root);
        crvContainer.setAdapter(
            new CommonAdapter<AudioInfo>(getContext(), audioInfo, R.layout.item_audio_info) {
                @Override
                protected void onBindViewHolder(CommonViewHolder holder, AudioInfo entity) {
                    final Uri uri = AudioInfoUtils.getUriFromId(entity.getId());
                    holder.setImageUri(getContext(), R.id.iv_cover, uri);
                    holder.setText(R.id.tv_title, entity.getTitle());
                    holder.setText(R.id.tv_artist, entity.getArtist());
                    holder.setOnClickListener(
                            v -> PlayerService.play(getContext(), entity, PlayerService.TYPE_LOCAL)
                    );
                }
            });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //适配icon大小
        ViewUtils.resizeDrawableLeft(
                getContext(),
                tvRandomPlay,
                R.drawable.list_btn_orange_randomplay,
                getResources().getInteger(R.integer.drawable_mid)
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        //扫描本地歌曲
        presenter.scanLocalAudios(this, getContext());
    }

    @OnClick(R.id.tv_random_play)
    public void onClick() {

    }

    @Override
    public void onSuccess(List<AudioInfo> data) {
        Logger.d("读取本地歌曲成功：" + data.size());
        crvContainer.notifyDataSetChanged(audioInfo, data, true, AudioInfo::getId);
    }

    @Override
    public void onFailed(String error) {
        crvContainer.notifyDataSetChanged(audioInfo, null, true, AudioInfo::getId);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onComplete() {

    }
}
