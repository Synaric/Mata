package com.synaric.app.player;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.synaric.app.player.event.PlayerStateChangedEvent;
import com.synaric.common.entity.AudioInfo;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 播放器服务。
 */
public class PlayerService extends Service {

    /**
     * 播放器状态：播放中。
     */
    public static final int STATE_PLAYING = 2;

    public static final String ACTION_INIT = "init";

    public static final String ACTION_PLAY = "play";

    public static final String EXTRA_AUDIO_INFO = "extraAudioInfo";

    public static final String EXTRA_TYPE = "extraType";

    public static final int TYPE_LOCAL = 0;

    public static final int TYPE_NET = 2;

    private static boolean INITIALIZED = false;

    private static MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 初始化播放器。
     */
    public static void init(Context context) {
        context = context.getApplicationContext();
        Intent intent = newIntent(context);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    /**
     * 播放音频。
     */
    public static void play(Context context, AudioInfo info, int sourceType) {
        context = context.getApplicationContext();
        Intent intent = newIntent(context);
        intent.setAction(ACTION_PLAY);
        intent.putExtra(EXTRA_TYPE, sourceType);
        intent.putExtra(EXTRA_AUDIO_INFO, info);
        context.startService(intent);
    }

    private static Intent newIntent(Context context) {
        return new Intent(context, PlayerService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_STICKY;

        String action = intent.getAction();
        if (ACTION_INIT.equals(action)) {
            handleInit();
            return START_STICKY;
        }

        if (!INITIALIZED) {
            Logger.e("MediaPlayer hasn't initialized.");
            return START_STICKY;
        }

        switch (action) {
            case ACTION_PLAY:
                handlePlay(intent);
                break;
        }

        return START_STICKY;
    }

    private void handleInit() {
        //仅初始化一次，防止泄露
        //由于onHandleIntent处理时其他请求被挂起，因此无需同步
        if (INITIALIZED) {
            Logger.e("init() should be called only once!");
            return;
        }
        mediaPlayer = new MediaPlayer();
        INITIALIZED = true;
        Logger.d("PlayerService has been initialized.");
    }

    private void handlePlay(Intent intent) {
        final AudioInfo audioInfo = (AudioInfo) intent.getSerializableExtra(EXTRA_AUDIO_INFO);
        final int type = intent.getIntExtra(EXTRA_TYPE, 0);
        if (audioInfo == null) return;

        final String url = audioInfo.getData();
        if (TextUtils.isEmpty(url)) {
            Logger.e("Unsupported url: " + url);
            return;
        }

        try {

            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Logger.d("PlayerService.onPrepared().");
                    HermesEventBus.getDefault().post(new PlayerStateChangedEvent(STATE_PLAYING, type, audioInfo));
                    //mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
