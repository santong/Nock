package com.santong.nock.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.santong.nock.fragments.NoticeFragment;
import com.santong.nock.fragments.PlanFragment;
import com.santong.nock.fragments.SettingsFragment;
import com.santong.nock.R;
import com.santong.nock.framework.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_plan;
    private LinearLayout ll_notice;
    private LinearLayout ll_settings;

    private View v_plan_select;
    private View v_notice_select;
    private View v_settings_select;

    private Fragment fg_plan;
    private Fragment fg_notice;
    private Fragment fg_settings;


    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelect(0);
        v_plan_select.setVisibility(View.VISIBLE);
    }

    private void initEvent() {
        ll_plan.setOnClickListener(this);
        ll_notice.setOnClickListener(this);
        ll_settings.setOnClickListener(this);
    }

    private void initData() {
        mContext = this;
    }

    private void initView() {
        ll_plan = (LinearLayout) findViewById(R.id.id_ll_plan);
        ll_notice = (LinearLayout) findViewById(R.id.id_ll_notice);
        ll_settings = (LinearLayout) findViewById(R.id.id_ll_settings);

        v_plan_select = findViewById(R.id.id_v_plan_select);
        v_notice_select = findViewById(R.id.id_v_notice_select);
        v_settings_select = findViewById(R.id.id_v_settings_select);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (fg_plan == null) {
                    fg_plan = new PlanFragment();
                    transaction.add(R.id.id_content, fg_plan);
                } else {
                    transaction.show(fg_plan);
                }
                break;
            case 1:
                if (fg_notice == null) {
                    fg_notice = new NoticeFragment();
                    transaction.add(R.id.id_content, fg_notice);
                } else {
                    transaction.show(fg_notice);
                }
                break;
            case 2:
                if (fg_settings == null) {
                    fg_settings = new SettingsFragment();
                    transaction.add(R.id.id_content, fg_settings);
                } else {
                    transaction.show(fg_settings);
                }
                break;
            default:
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (fg_plan != null) {
            transaction.hide(fg_plan);
        }
        if (fg_notice != null) {
            transaction.hide(fg_notice);
        }
        if (fg_settings != null) {
            transaction.hide(fg_settings);
        }
    }

    @Override
    public void onClick(View v) {
        resetView();
        switch (v.getId()) {
            case R.id.id_ll_plan:
                v_plan_select.setVisibility(View.VISIBLE);
                setSelect(0);
                break;
            case R.id.id_ll_notice:
                v_notice_select.setVisibility(View.VISIBLE);
                setSelect(1);
                break;
            case R.id.id_ll_settings:
                v_settings_select.setVisibility(View.VISIBLE);
                setSelect(2);
                break;
            default:
                break;
        }
    }

    private void resetView() {
        v_plan_select.setVisibility(View.INVISIBLE);
        v_notice_select.setVisibility(View.INVISIBLE);
        v_settings_select.setVisibility(View.INVISIBLE);
    }

}
