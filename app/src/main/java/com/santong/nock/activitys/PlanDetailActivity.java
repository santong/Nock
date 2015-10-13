package com.santong.nock.activitys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.santong.nock.R;
import com.santong.nock.model.NockPlan;
import com.santong.nock.utils.DataBaseHelper;

/**
 * Created by santong.
 * At 15/10/13 16:42
 */
public class PlanDetailActivity extends FragmentActivity {

    private Context mContext;

    private DataBaseHelper dbHelper;

    private

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);


        initView();
        initData();
        initEvent();

//        TextView tv = (TextView) findViewById(R.id.id_tv_tt);
//        tv.setText(plan.toString());
    }

    private void initEvent() {

    }

    private void initData() {
        mContext = this;

        dbHelper = new DataBaseHelper(mContext, DataBaseHelper.DATABASE_NAME, null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        int id = intent.getIntExtra("planId", -1);

        if (id >= 0) {
            Cursor cursor = db.rawQuery("select * from " + DataBaseHelper.TABLE_NAME_PLAN + " where id = " + id, null);
            NockPlan plan = DataBaseHelper.Cursor2Plan(cursor);
        }
    }

    private void initView() {

    }
}
