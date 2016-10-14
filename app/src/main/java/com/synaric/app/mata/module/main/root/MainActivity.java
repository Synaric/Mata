package com.synaric.app.mata.module.main.root;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.orhanobut.logger.Logger;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpActivity;
import com.synaric.app.mata.event.RequestToggleDrawer;

import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;

/**
 * 主界面。
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView<String> {

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        //采用单Activity + 多Fragment的模式
        loadRootFragment(R.id.fl_container, HomeFragment.newInstance());
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
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

    @Subscribe
    public void onEvent(RequestToggleDrawer event) {
        if(!event.isToggleOpen()) return;
        if (drawerLayout != null && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
