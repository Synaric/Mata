package com.synaric.app.mata.module.local.audio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.synaric.app.mata.R;
import com.synaric.app.mata.base.BaseFragment;
import com.synaric.app.widget.ViewUtils;
import com.synaric.common.entity.AudioInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 本地歌曲-歌曲界面。
 * <br/><br/>Created by Synaric on 2016/10/26 0026.
 */
public class AudioFragment extends BaseFragment {

    @InjectView(R.id.tv_random_play)
    TextView tvRandomPlay;
    @InjectView(R.id.rv_container)
    RecyclerView rvContainer;

    private List<AudioInfo> audioInfos = new ArrayList<>();

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

        rvContainer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvContainer.setAdapter(new MyAdapter<>(audioInfos));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //适配icon大小
        ViewUtils.resizeDrawableLeft(
                getContext(), tvRandomPlay, R.drawable.list_btn_orange_randomplay, 14
        );

    }

    @OnClick(R.id.tv_random_play)
    public void onClick() {

    }

    private class MyAdapter<T> extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private LayoutInflater inflater = LayoutInflater.from(getContext());

        private List<T> data;

        public MyAdapter(List<T> data) {
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_audio_info, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView ivCover;
            public TextView tvTitle;
            public TextView tvArtist;
            public ImageView ivMore;

            public MyViewHolder(View itemView) {
                super(itemView);
                ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                tvArtist = (TextView) itemView.findViewById(R.id.tv_artist);
                ivMore = (ImageView) itemView.findViewById(R.id.iv_more);
            }
        }
    }
}
