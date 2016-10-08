package com.synaric.app.mata.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.synaric.app.mata.R;

/**
 * 拥有ToolBar的Activity。
 * Created by Synaric on 2016/9/26 0026.
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate() {
        super.onCreate();
        initToolBar();
    }

    /**
     * 在这里初始化ToolBar。
     */
    public void initToolBar() {
        //toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
        }
    }
}
