package com.santong.nock.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.santong.nock.R;
import com.santong.nock.framework.BaseActivity;
import com.santong.nock.model.NockPlan;

/**
 * Created by santong.
 * At 15/10/20 16:55
 */
public class PlanReportActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_plan_report;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = (TextView) findViewById(R.id.id_tv_plan_details);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        NockPlan plan = bundle.getParcelable("plan");
        textView.setText("施工中... ...");
    }
}
