package com.synaric.app.player;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.IOException;

/**
 * 播放器服务。
 */
public class PlayerService extends IntentService {

    public static final String ACTION_INIT = "init";

    public static final String ACTION_PLAY = "play";

    public static final String EXTRA_FILE = "extraFile";

    public static final String EXTRA_URL = "extraUrl";

    public static final String EXTRA_TYPE = "extraType";

    public static final int TYPE_LOCAL = 0;

    public static final int TYPE_NET = 2;

    private static boolean INITIALIZED = false;

    private MediaPlayer mediaPlayer;

    public PlayerService() {
        super("PlayerService");
    }

    /**
     * 初始化播放器。
     */
    public static void init(Context context) {
        Intent intent = newIntent(context.getApplicationContext());
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    /**
     * 播放本地音频。
     */
    public static void playLocal(Context context, String filepath) {
        play(context, filepath, TYPE_LOCAL);
    }

    /**
     * 播放网络音频。
     */
    public static void playNet(Context context, String url) {
        play(context, url, TYPE_NET);
    }

    /**
     * 播放音频。
     *
     * @param content 网络音频填写URL，本地音频填写文件路径。
     */
    public static void play(Context context, String content, int from) {
        Intent intent = newIntent(context.getApplicationContext());
        intent.setAction(ACTION_PLAY);
        intent.putExtra(EXTRA_TYPE, from);
        if (from == TYPE_LOCAL) {
            intent.putExtra(EXTRA_FILE, content);
        } else if (from == TYPE_NET) {
            intent.putExtra(EXTRA_URL, content);
        }
        context.startService(intent);
    }

    private static Intent newIntent(Context context) {
        return new Intent(context, IntentService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) return;

        String action = intent.getAction();
        if (ACTION_INIT.equals(action)) {
            handleInit();
            return;
        }

        if (!INITIALIZED) {
            Logger.e("MediaPlayer hasn't initialized.");
            return;
        }

        switch (action) {
            case ACTION_PLAY:
                handlePlay(intent);
                break;
        }
    }

    private void handleInit() {
        //初始化一次，防止泄露
        //由于onHandleIntent处理时其他请求被挂起，因此无需同步
        if (INITIALIZED) {
            Logger.e("init() should be called only once!");
            return;
        }
        mediaPlayer = new MediaPlayer();
        INITIALIZED = true;
    }

    private void handlePlay(Intent intent) {
        final String url = intent.getStringExtra(EXTRA_URL);
        if (TextUtils.isEmpty(url)) {
            Logger.e("Unsupported url: " + url);
            return;
        }

        Uri uri = Uri.parse(url);
        try {

            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
