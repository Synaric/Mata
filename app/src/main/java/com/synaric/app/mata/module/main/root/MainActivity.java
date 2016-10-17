package com.synaric.app.mata.module.main.root;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.orhanobut.logger.Logger;
import com.synaric.app.mata.R;
import com.synaric.app.mata.base.BaseFragment;
import com.synaric.app.mata.base.MvpActivity;
import com.synaric.app.mata.event.RequestToggleDrawer;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import me.yokeyword.fragmentation.debug.DebugFragmentRecord;

/**
 * 主界面。
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView<String>, BaseFragment.OnLockDrawLayoutListener {

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

    /**
     * 退栈到HomeFragment的时候，允许DrawerLayout滑动，反之禁止。
     * @param lock Fragment请求是否锁定DrawerLayout。
     */
    @Override
    public void onLockDrawLayout(boolean lock) {
        if (lock) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            List<DebugFragmentRecord> records = getFragmentRecords();
            if(records == null || records.size() != 2) return;
            /*
                这种情况下，onLockDrawLayout在onDestroyView中被调用。
                此时records.size()为2，但是实际上栈顶的Fragment将要退栈。
             */
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
