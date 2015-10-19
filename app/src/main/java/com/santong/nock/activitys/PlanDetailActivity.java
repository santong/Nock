package com.santong.nock.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.santong.nock.R;
import com.santong.nock.framework.BaseActivity;
import com.santong.nock.model.NockPlan;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.Date;

/**
 * Created by santong.
 * At 15/10/13 16:42
 */
public class PlanDetailActivity extends BaseActivity implements View.OnClickListener {

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
    private NockPlan plan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_plan_detail;
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
            plan = dbHelper.getPlan(id);

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
        switch (v.getId()) {
            case R.id.id_btn_save_plan:
                doSave();
                break;
            case R.id.id_btn_del_plan:
                doDelete();
                break;
            default:
                break;
        }
    }

    private void doSave() {
        String des = edt_plan_des.getText().toString();
        Date end_Date = DateUtils.getDateFromStr(tv_plan_end_date.getText().toString());

        if (isEmpty(des))
            plan.setDescription(des);
        if (isEmpty(end_Date + ""))
            plan.setEndDate(end_Date);
        if (dbHelper.UpdatePlan(plan)) {
            pushActivity(HomeActivity.class);
            Toast.makeText(mContext, "更新成功", Toast.LENGTH_SHORT).show();
            finish();
        } else
            Toast.makeText(mContext, "保存失败，请检查信息填写", Toast.LENGTH_SHORT).show();
    }

    private void doDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("注意:");
        builder.setMessage("确认删除此计划吗？");
        builder.setPositiveButton("删吧删吧...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dbHelper.DeletePlan(id)) {
                    pushActivity(HomeActivity.class);
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(mContext, "删除失败，请稍后再试", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("我不想删了!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
