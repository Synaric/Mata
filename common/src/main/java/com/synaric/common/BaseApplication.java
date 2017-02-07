package com.synaric.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.synaric.common.utils.SPUtils;
import com.synaric.common.utils.SystemUtils;
import com.synaric.dex.LoadResActivity;

/**
 * 基础配置初始化。
 * 针对MultiDex第一次启动APP有一定优化。
 * Created by Synaric on 2016/9/21 0021.
 */
public abstract class BaseApplication extends Application{

    protected abstract String getBaseUrl();

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private static final byte[] lock = new byte[0];

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化工具
        CommonUtilsConfiguration.init(this, getBaseUrl());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //Android 5.0以上不需要调用MultiDex
        if (!isAsyncLaunchProcess() && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (needWait(base)) {
                /*
                    第一次启动APP由于MultiDex将会非常缓慢，某些低端机可能ANR。
                    因此这里的做法是挂起主进程，开启:async_launch进程执行dexopt。
                    dexopt执行完毕，主进程重新变为前台进程，继续执行初始化。
                    主进程在这过程中变成后台进程，因此阻塞将不会引起ANR。
                 */
                DexInstallDeamonThread thread = new DexInstallDeamonThread(this, base);
                thread.start();

                //阻塞等待:async_launch完成加载
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                thread.exit();
                Log.d("BaseApplication", "dexopt finished. alloc MultiDex.install()");
            } else {
                MultiDex.install(this);
            }
        }
    }

    public boolean isAsyncLaunchProcess() {
        String processName = SystemUtils.getCurrentProcessName(this);
        return processName != null && processName.contains(":async_launch");
    }

    @SuppressWarnings("deprecation")
    private boolean needWait(Context context) {
        SharedPreferences sp = SPUtils.getVersionSharedPreferences(context, Context.MODE_MULTI_PROCESS);
        return sp.getBoolean(BaseSPKey.FIRST_LAUNCH, true);
    }

    /**
     * @see <a href="http://www.open-open.com/lib/view/open1452264136714.html">其实你不知道MultiDex到底有多坑</a>
     */
    private static class DexInstallDeamonThread extends Thread {

        private Handler handler;

        private Context application;

        private Context base;

        private Looper looper;

        public DexInstallDeamonThread(Context application, Context base) {
            this.application = application;
            this.base = base;
        }

        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            Looper.prepare();
            looper = Looper.myLooper();
            handler = new Handler() {

                @SuppressWarnings("deprecation")
                @Override
                public void handleMessage(Message msg) {
                    Log.d("BaseApplication", "handle: MultiDex install finish");
                    synchronized (lock) {
                        lock.notify();
                    }
                    SPUtils
                        .getVersionSharedPreferences(application, Context.MODE_MULTI_PROCESS)
                        .edit()
                        .putBoolean(BaseSPKey.FIRST_LAUNCH, false)
                        .apply();
                }
            };

            Messenger messenger = new Messenger(handler);
            Intent intent = new Intent(base, LoadResActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("MESSENGER", messenger);
            base.startActivity(intent);
            Looper.loop();
        }

        public void exit() {
            if (looper != null) looper.quit();
        }
    }
}
