package com.santong.nock.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.santong.nock.R;
import com.santong.nock.activitys.AddNoticeActivity;
import com.santong.nock.adapter.NoticeListAdapter;
import com.santong.nock.framework.BaseFragment;
import com.santong.nock.model.NockNotice;
import com.santong.nock.model.NockPlan;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santong.
 * At 15/9/27 18:57
 */
public class NoticeFragment extends BaseFragment implements View.OnClickListener {

    private View view;

    private ListView lv_notices;

    private FloatingActionButton fa_btn_add_notice;

    private List<NockNotice> noticeList = new ArrayList<>();

    private DataBaseHelper dbHelper;

    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        mContext = getActivity();

        initView();
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
        initNoticeList();
        initNoticeListEvent();
    }

    private void initView() {
        lv_notices = (ListView) view.findViewById(R.id.id_ll_notice);
        fa_btn_add_notice = (FloatingActionButton) view.findViewById(R.id.id_fa_add_notice);
    }

    private void initEvent() {
        fa_btn_add_notice.setOnClickListener(this);
    }

    private void initData() {
        NoticeListAdapter noticeListAdapter = new NoticeListAdapter(mContext, R.layout.cell_notice_list, noticeList);
        lv_notices.setAdapter(noticeListAdapter);

        dbHelper = DataBaseHelper.GetInstance(mContext, null);
    }

    private void initNoticeList() {
        noticeList.clear();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DataBaseHelper.TABLE_NAME_NOTICE, null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                NockNotice notice = new NockNotice();
                notice.setNoticeId(cursor.getInt(cursor.getColumnIndex("id")));
                notice.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                notice.setContent(cursor.getString(cursor.getColumnIndex("content")));
                notice.setCreateDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("create_date"))));
                if (null != cursor.getString(cursor.getColumnIndex("notice_date")))
                    notice.setNoticeDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("notice_date"))));
                int flag = cursor.getInt(cursor.getColumnIndex("state"));
                if (flag == 1)
                    notice.setIsComplete(true);
                else
                    notice.setIsComplete(false);
                noticeList.add(notice);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void initNoticeListEvent() {
        lv_notices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_fa_add_notice) {
            Intent intent = new Intent(getActivity(), AddNoticeActivity.class);
            startActivity(intent);
        }
    }
}
