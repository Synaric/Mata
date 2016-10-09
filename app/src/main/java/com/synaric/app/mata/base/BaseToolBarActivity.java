package com.synaric.app.mata.base;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.synaric.app.mata.R;
import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.app.widget.ActionButton;

/**
 * 拥有ToolBar的Activity。
 * Created by Synaric on 2016/9/26 0026.
 */
public abstract class BaseToolBarActivity<P extends BasePresenter> extends MvpActivity<P>  {

    protected Toolbar toolbar;

    protected DrawerLayout drawerLayout;

    protected ActionBarDrawerToggle drawerToggle;

    private ActionButton actionButton;

    @Override
    protected void onCreate() {
        super.onCreate();
        actionButton = ActionButton.getOrCreate(BaseToolBarActivity.this);
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
        drawerLayout.addDrawerListener(new LocalDrawerListener());
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
        return R.menu.default_player;
    }

    /**
     * 侧边栏打开将隐藏{@link ActionButton}，反之则显示。
     */
    private class LocalDrawerListener extends DrawerLayout.SimpleDrawerListener{

        private int oldState = DrawerLayout.STATE_IDLE;

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            actionButton.setAlpha(1 - slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            actionButton.setVisibility(View.INVISIBLE);
            actionButton.setEnabled(false);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if(oldState == DrawerLayout.STATE_IDLE && newState == DrawerLayout.STATE_DRAGGING) {
                actionButton.setVisibility(View.VISIBLE);
                actionButton.setEnabled(true);
            }
            oldState = newState;
        }
    }
}
