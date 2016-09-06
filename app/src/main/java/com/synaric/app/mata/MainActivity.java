package com.synaric.app.mata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.synaric.app.mata.ui.base.BaseActivity;
import com.synaric.app.mata.ui.experimental.ECaptureActivity;
import com.synaric.app.rxmodel.DbModel;
import com.synaric.app.rxmodel.RxModel;
import com.synaric.app.rxmodel.filter.Filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, ECaptureActivity.class));
    }
}
