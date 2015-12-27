package com.santong.nock.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santong.nock.R;
import com.santong.nock.activitys.AboutNockActivity;
import com.santong.nock.framework.BaseFragment;

/**
 * Created by santong.
 * At 15/9/27 18:57
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;

    private View view;

    private TextView tv_about;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        initView();
        initData();
        initEvent();
        return view;
    }

    private void initView() {
        tv_about = (TextView) view.findViewById(R.id.id_settings_tv_about);
    }

    private void initData() {
        mContext = getActivity();
    }

    private void initEvent() {
        tv_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_settings_tv_about:
                Intent intent = new Intent(getActivity(), AboutNockActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
