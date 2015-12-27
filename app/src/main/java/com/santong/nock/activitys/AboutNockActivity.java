package com.santong.nock.activitys;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.santong.nock.R;
import com.santong.nock.framework.BaseActivity;

/**
 * Created by santong.
 * At 15/12/27 14:47
 */
public class AboutNockActivity extends BaseActivity {

    private TextView tv_appVersion;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_about_nock;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        try {
            tv_appVersion.setText("版本号:" + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_appVersion = (TextView) this.findViewById(R.id.id_about_tv_version);
    }
}
