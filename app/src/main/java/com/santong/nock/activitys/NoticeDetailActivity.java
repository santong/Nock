package com.santong.nock.activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
 * At 15/12/26 12:22
 */
public class NoticeDetailActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;

    private EditText edt_noticeTitle;
    private EditText edt_noticeContent;

    private TextView tv_noticeCreateDate;
    private TextView tv_noticeDate;
    private TextView tv_warn;

    private Button btn_saveNotice;
    private Button btn_delNotice;

    private DataBaseHelper dbHelper;

    private NockNotice nockNotice;

    private int year, month, day;
    private Date noticeDate;

    private int ID;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        edt_noticeTitle = (EditText) this.findViewById(R.id.id_edt_notice_title);
        edt_noticeContent = (EditText) this.findViewById(R.id.id_edt_notice_des);

        tv_noticeCreateDate = (TextView) this.findViewById(R.id.id_tv_notice_create_date);
        tv_noticeDate = (TextView) this.findViewById(R.id.id_tv_notice_date);
        tv_warn = (TextView) this.findViewById(R.id.id_tv_warn_title);

        btn_saveNotice = (Button) this.findViewById(R.id.id_btn_save_notice);
        btn_delNotice = (Button) this.findViewById(R.id.id_btn_del_notice);
    }

    private void initData() {
        mContext = this;

        dbHelper = DataBaseHelper.GetInstance(mContext, null);

        Intent intent = getIntent();

        ID = intent.getIntExtra("noticeId", -1);
        if (ID >= 0) {
            nockNotice = dbHelper.getNotice(ID);

            edt_noticeTitle.setText(nockNotice.getTitle());
            if (!TextUtils.isEmpty(nockNotice.getContent()))
                edt_noticeContent.setText(nockNotice.getContent());
            if (null != nockNotice.getCreateDate())
                tv_noticeCreateDate.setText(DateUtils.formatDate(nockNotice.getCreateDate()));
            if (null != nockNotice.getNoticeDate())
                tv_noticeDate.setText(DateUtils.formatDate(nockNotice.getNoticeDate()));
        }

        initDate();
    }

    private void initEvent() {
        tv_noticeDate.setOnClickListener(this);
        btn_saveNotice.setOnClickListener(this);
        btn_delNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        tv_warn.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.id_tv_notice_date:
                selectDate(tv_noticeDate);
                break;
            case R.id.id_btn_save_notice:
                doSave();
                break;
            case R.id.id_btn_del_notice:
                doDel();
                break;
        }
    }

    private void doSave() {
        boolean flag = true;
        if (!TextUtils.isEmpty(edt_noticeTitle.getText()))
            nockNotice.setTitle(edt_noticeTitle.getText().toString());
        else {
            tv_warn.setVisibility(View.VISIBLE);
            flag = false;
        }
        if (!TextUtils.isEmpty(edt_noticeContent.getText()))
            nockNotice.setContent(edt_noticeContent.getText().toString());
        if (!TextUtils.isEmpty(tv_noticeDate.getText()))
            nockNotice.setNoticeDate(noticeDate);

        if (flag) {
            dbHelper.updateNotice(nockNotice);
            pushActivity(HomeActivity.class);
            finish();
        }
    }

    private void doDel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("注意:");
        builder.setMessage("确认删除此备忘吗？");
        builder.setPositiveButton("删吧删吧...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dbHelper.delNotice(ID)) {
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
                noticeDate = calendar.getTime();
                textView.setText(DateUtils.formatDate(calendar.getTime()));
            }
        }, year, month, day);
        dpd.show();
    }
}
