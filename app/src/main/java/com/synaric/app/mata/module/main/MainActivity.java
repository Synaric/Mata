package com.synaric.app.mata.module.main;

import android.support.v7.app.ActionBarDrawerToggle;

import com.orhanobut.logger.Logger;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpActivity;
import com.synaric.app.widget.ActionButton;

/**
 * 主界面。
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView<String>{

    private ActionBarDrawerToggle drawerToggle;
    private ActionButton actionButton;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
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
    public void onSuccess(String data) {
        Logger.d(data);
    }

    @Override
    public void onFailed(String error) {
        Logger.d(error);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    protected void onCreate() {
        actionButton = new ActionButton(this);
        actionButton.show();
        presenter.loadMain();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(actionButton != null) actionButton.hide();
    }
}
