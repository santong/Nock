package com.santong.nock.activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.santong.nock.R;
import com.santong.nock.framework.BaseActivity;
import com.santong.nock.model.NockNotice;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by santong.
 * At 15/12/25 17:55
 */
public class AddNoticeActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;

    private EditText edt_noticeTitle;
    private EditText edt_noticeContent;

    private TextView tv_noticeDate;
    private TextView tv_warn;

    private Button btn_addNotice;

    private int year;
    private int month;
    private int day;

    private Date noticeDate;
    private Date createDate;

    private DataBaseHelper dbHelper;

    private NockNotice nockNotice = new NockNotice();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_notice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        edt_noticeTitle = (EditText) this.findViewById(R.id.id_edt_add_notice_title);
        edt_noticeContent = (EditText) this.findViewById(R.id.id_edt_add_notice_des);

        tv_noticeDate = (TextView) this.findViewById(R.id.id_tv_add_notice_date);
        tv_warn = (TextView) this.findViewById(R.id.id_tv_notice_warn_title);

        btn_addNotice = (Button) this.findViewById(R.id.id_btn_add_notice);
    }

    private void initData() {
        mContext = this;
        dbHelper = DataBaseHelper.GetInstance(mContext, null);

        initDate();
    }

    /**
     * 初始化日期
     */
    private void initDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        createDate = calendar.getTime();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void initEvent() {
        tv_noticeDate.setOnClickListener(this);
        btn_addNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_add_notice:
                if (isValidate()){
                    dbHelper.saveNotice(nockNotice);
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.id_tv_add_notice_date:
                selectDate(tv_noticeDate);
                break;
        }
    }

    private boolean isValidate() {
        boolean flag;
        tv_warn.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(edt_noticeTitle.getText())) {
            nockNotice.setTitle(edt_noticeTitle.getText().toString());
            flag = true;
        } else {
            tv_warn.setVisibility(View.VISIBLE);
            flag = false;
        }
        if (!TextUtils.isEmpty(edt_noticeContent.getText())) {
            nockNotice.setContent(edt_noticeContent.getText().toString());
        }
        if (!TextUtils.isEmpty(tv_noticeDate.getText())) {
            nockNotice.setNoticeDate(noticeDate);
        }
        nockNotice.setCreateDate(createDate);
        return flag;
    }

    private void selectDate(final TextView textView) {
        DatePickerDialog dpd = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Year, int MonthOfYear, int DayOfMonth) {
                year = Year;
                month = MonthOfYear;
                day = DayOfMonth;
                GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                noticeDate = calendar.getTime();
                textView.setText(DateUtils.formatDate(calendar.getTime()));
            }
        }, year, month, day);
        dpd.show();
    }
}
