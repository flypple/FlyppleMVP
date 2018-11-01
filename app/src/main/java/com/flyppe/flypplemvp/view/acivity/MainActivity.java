package com.flyppe.flypplemvp.view.acivity;

import android.support.v4.app.ActivityCompat;

import com.flyppe.flypplemvp.R;
import com.flyppe.flypplemvp.basic.activity.BaseActivity;
import com.flyppe.flypplemvp.contract.MainContract;
import com.flyppe.flypplemvp.presenter.MainPresenter;

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
