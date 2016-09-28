package com.synaric.app.mata.ui;

import android.support.v7.app.ActionBarDrawerToggle;

import com.synaric.app.mata.R;
import com.synaric.app.mata.ui.base.BaseActivity;
import com.synaric.app.widget.ActionButton;

public class MainActivity extends BaseActivity {

    private ActionBarDrawerToggle drawerToggle;
    private ActionButton actionButton;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initToolBar() {
//        toolbar.setTitle(R.string.app_name);
//        setSupportActionBar(toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayUseLogoEnabled(false);
//        }
//
//        drawerToggle = new ActionBarDrawerToggle(this,
//                drawerLayout,
//                toolbar,
//                R.string.app_name,
//                R.string.app_name
//        );
//        drawerLayout.post(() -> drawerToggle.syncState());
//        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onCreate() {
        actionButton = new ActionButton(this);
        actionButton.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(actionButton != null) actionButton.hide();
    }
}
