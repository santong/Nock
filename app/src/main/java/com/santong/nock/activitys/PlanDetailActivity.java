package com.santong.nock.activitys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.santong.nock.R;
import com.santong.nock.model.NockPlan;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.Date;

/**
 * Created by santong.
 * At 15/10/13 16:42
 */
public class PlanDetailActivity extends FragmentActivity implements View.OnClickListener {

    private Context mContext;

    private TextView tv_plan_title;
    private TextView tv_plan_start_date;
    private TextView tv_plan_end_date;

    private EditText edt_plan_des;

    private Button btn_confirm;
    private Button btn_del;

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_confirm.setOnClickListener(this);
        btn_del.setOnClickListener(this);
    }

    private void initData() {
        mContext = this;

        dbHelper = new DataBaseHelper(mContext, null);
        db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        id = intent.getIntExtra("planId", -1);

        if (id >= 0) {
            Cursor cursor = db.rawQuery("select * from " + DataBaseHelper.TABLE_NAME_PLAN + " where id = " + id, null);
            NockPlan plan = DataBaseHelper.Cursor2Plan(cursor);

            tv_plan_title.setText(plan.getTitle());
            tv_plan_start_date.setText(DateUtils.formatDate(plan.getStartDate()));
            tv_plan_end_date.setText(DateUtils.formatDate(plan.getEndDate()));

            edt_plan_des.setText(plan.getDescription());
        }
    }

    private void initView() {
        tv_plan_title = (TextView) findViewById(R.id.id_tv_plan_title);
        tv_plan_start_date = (TextView) findViewById(R.id.id_tv_plan_start_date);
        tv_plan_end_date = (TextView) findViewById(R.id.id_tv_plan_end_date);

        edt_plan_des = (EditText) findViewById(R.id.id_edt_plan_des);

        btn_confirm = (Button) findViewById(R.id.id_btn_save_plan);
        btn_del = (Button) findViewById(R.id.id_btn_del_plan);
    }

    @Override
    public void onClick(View v) {
        String des = edt_plan_des.getText().toString();
        Date end_Date = DateUtils.getDateFromStr(tv_plan_end_date.getText().toString());
        switch (v.getId()) {
            case R.id.id_btn_save_plan:
                if (dbHelper.UpdatePlan(id, end_Date, des)){
                    Toast.makeText(mContext,"更新成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(mContext,"保存失败，请检查信息填写",Toast.LENGTH_SHORT).show();

                break;
            case R.id.id_btn_del_plan:
                break;
            default:
                break;
        }
    }
}
