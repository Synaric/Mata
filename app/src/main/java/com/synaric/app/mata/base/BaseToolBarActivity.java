package com.synaric.app.mata.base;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.synaric.app.mata.R;
import com.synaric.app.mata.mvp.BasePresenter;

/**
 * 拥有ToolBar的Activity。
 * Created by Synaric on 2016/9/26 0026.
 */
public abstract class BaseToolBarActivity<P extends BasePresenter> extends MvpActivity<P>  {

    protected Toolbar toolbar;

    protected DrawerLayout drawerLayout;

    protected ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate() {
        super.onCreate();
        initToolBar();
    }

    /**
     * 在这里初始化ToolBar。
     */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.post(() -> drawerToggle.syncState());
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuId(), menu);
        return true;
    }

    /**
     * 设置侧边栏布局。
     */
    protected int getMenuId() {
        return R.menu.main;
    }
}
