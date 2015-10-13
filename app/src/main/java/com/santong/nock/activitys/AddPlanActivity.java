package com.santong.nock.activitys;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.santong.nock.R;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by santong.
 * At 15/9/28 16:39
 */
public class AddPlanActivity extends FragmentActivity implements View.OnClickListener {

    private Context mContext;

    private EditText edt_plan_title;
    private EditText edt_plan_des;

    private TextView tv_start_date;
    private TextView tv_end_date;

    private TextView tv_warn_title;
    private TextView tv_warn_date;

    private Button btn_add_plan;

    private int year;
    private int month;
    private int day;

    private DataBaseHelper dbHelper;

    private String title;
    private String startDateStr;
    private String endDateStr;
    private String description;

    private Date startDate;
    private Date endDate;

    private int recordDays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        initView();
        initData();
        initEvent();
    }


    private void initData() {
        mContext = this;

        dbHelper = new DataBaseHelper(mContext, DataBaseHelper.DATABASE_NAME, null, 1);

        initDate();     // 初始日期
    }

    private void initEvent() {
        btn_add_plan.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
    }

    private void initView() {
        edt_plan_title = (EditText) findViewById(R.id.id_edt_add_plan_title);
        edt_plan_des = (EditText) findViewById(R.id.id_edt_add_plan_des);

        tv_start_date = (TextView) findViewById(R.id.id_tv_add_plan_start_date);
        tv_end_date = (TextView) findViewById(R.id.id_tv_add_plan_end_date);

        tv_warn_title = (TextView) findViewById(R.id.id_tv_warn_title);
        tv_warn_date = (TextView) findViewById(R.id.id_tv_warn_date);

        btn_add_plan = (Button) findViewById(R.id.id_btn_add_plan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_add_plan_start_date:
                selectDate(tv_start_date);
                break;
            case R.id.id_tv_add_plan_end_date:
                selectDate(tv_end_date);
                break;
            case R.id.id_btn_add_plan:
                if (isComplete()) {
                    savePlan();
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化日期
     */
    private void initDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void selectDate(final TextView textView) {
        DatePickerDialog dpd = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Year, int MonthOfYear, int DayOfMonth) {
                year = Year;
                month = MonthOfYear;
                day = DayOfMonth;
                GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                textView.setText(DateUtils.formatDate(calendar.getTime()) + "");
            }
        }, year, month, day);
        dpd.show();
    }

    /**
     * 展示警告框
     * 0 ---> title的警告框
     * 1 ---> date的警告框
     */
    private void showWarning(int warnViewID, String warnStr) {
        switch (warnViewID) {
            case 0:
                tv_warn_title.setText(warnStr);
                tv_warn_title.setVisibility(View.VISIBLE);
                break;
            case 1:
                tv_warn_date.setText(warnStr);
                tv_warn_date.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 隐藏日期
     */
    private void hideWarning() {
        tv_warn_title.setVisibility(View.GONE);
        tv_warn_date.setVisibility(View.GONE);
    }

    /**
     * 判断表单是否合法
     */
    private boolean isComplete() {
        hideWarning();
        getPlanInfo();

        boolean flag = true;

        if (TextUtils.isEmpty(title)) {
            flag = false;
            showWarning(0, getResources().getString(R.string.plan_warn_title_is_empty));
        } else if (TextUtils.isEmpty(startDateStr) || TextUtils.isEmpty(endDateStr)) {
            flag = false;
            showWarning(1, getResources().getString(R.string.plan_warn_date_is_empty));
        } else if (startDate.after(endDate)) {
            flag = false;
            showWarning(1, getResources().getString(R.string.plan_warn_judge_date));
        }
        return flag;
    }

    /**
     * 获得表单信息
     */
    private void getPlanInfo() {
        title = edt_plan_title.getText().toString();
        startDateStr = tv_start_date.getText().toString();
        endDateStr = tv_end_date.getText().toString();
        description = edt_plan_des.getText().toString();

        if (!TextUtils.isEmpty(startDateStr))
            startDate = DateUtils.getDateFromStr(startDateStr);
        if (!TextUtils.isEmpty(endDateStr))
            endDate = DateUtils.getDateFromStr(endDateStr);
        if (!TextUtils.isEmpty(startDateStr) && !TextUtils.isEmpty(endDateStr))
            recordDays = DateUtils.getDaysFrom2Date(startDate, endDate);
    }

    private void savePlan() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //执行到此方法时，下面四个属性一定是非空的，所以不用判断
        values.put("title", title);
        values.put("start_date", startDateStr);
        values.put("end_date", endDateStr);
        values.put("record_days", recordDays);

        if (!TextUtils.isEmpty(description))
            values.put("description", description);
        else
            values.put("description", getResources().getString(R.string.plan_des_is_empty));
        db.insert(DataBaseHelper.TABLE_NAME_PLAN, null, values);
        values.clear();
    }
}
