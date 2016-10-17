package com.synaric.app.player;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.orhanobut.logger.Logger;

/**
 * 播放器服务。
 */
public class PlayerService extends IntentService {

    public static final String ACTION_INIT = "actionInit";

    public static final String ACTION_PLAY = "actionPlay";

    public static final String EXTRA_URL = "extraUrl";

    private static boolean INITIALIZED = false;

    private MediaPlayer mediaPlayer;

    public PlayerService() {
        super("PlayerService");
    }

    /**
     * 初始化播放器。
     */
    public static void actionInit(Context context) {
        Intent intent = newIntent(context);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    /**
     * 播放网络音频。
     * @param url 音频的URL。
     */
    public static void actionPlay(Context context, String url) {
        Intent intent = newIntent(context);
        intent.setAction(ACTION_PLAY);
        intent.putExtra(EXTRA_URL, url);
        context.startService(intent);
    }

    private static Intent newIntent(Context context) {
        return new Intent(context, IntentService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent == null) return;

        String action = intent.getAction();
        if(ACTION_INIT.equals(action)) {
            handleInit();
            return;
        }

        if(!INITIALIZED) {
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
        if(INITIALIZED) {
            Logger.e("actionInit() should be called only once!");
            return;
        }
        mediaPlayer = new MediaPlayer();
        INITIALIZED = true;
    }

    private void handlePlay(Intent intent) {
        final String url = intent.getStringExtra(EXTRA_URL);

    }
}
