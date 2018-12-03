package com.flypple.flypplemvp.view.acivity;

import android.support.v4.app.ActivityCompat;

import com.flypple.flypplemvp.R;
import com.flypple.flypplemvp.basic.activity.BaseActivity;
import com.flypple.flypplemvp.contract.MainContract;
import com.flypple.flypplemvp.presenter.MainPresenter;

/**
 *
 * Created by flypple on 2018/8/18.
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private static final String TAG = "MainActivity";

    @Override
    protected void onInit() {
        super.onInit();

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }
}
