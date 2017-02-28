package com.synaric.dex;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.synaric.app.common.R;

/**
 * 空Activity，在独立进程中运行，用于第一次加打开APP时异步执行dexopt。
 */
public class LoadResActivity extends AppCompatActivity {

    private Messenger messenger;
    private LoadDexTask dexTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
        setContentView(R.layout.activity_load_res);

        Log.d("LoadResActivity", "start install");
        Intent from = getIntent();
        messenger = from.getParcelableExtra("MESSENGER");

        dexTask = new LoadDexTask();
        dexTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dexTask != null) dexTask.cancel(true);
    }

    class LoadDexTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MultiDex.install(getApplication());
                Log.d("LoadResActivity", "finish install");
                messenger.send(new Message());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        //无法退出
    }
}
