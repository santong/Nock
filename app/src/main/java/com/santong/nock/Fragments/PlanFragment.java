package com.santong.nock.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.santong.nock.R;
import com.santong.nock.activitys.AddPlanActivity;
import com.santong.nock.activitys.PlanDetailActivity;
import com.santong.nock.adapter.PlanListAdapter;
import com.santong.nock.model.NockPlan;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santong.
 * At 15/9/27 18:57
 */
public class PlanFragment extends Fragment implements View.OnClickListener {

    private View view;

    private ListView lv_plans;

    private ImageButton img_btn_add_plan;

    private List<NockPlan> plans = new ArrayList<>();

    private DataBaseHelper dbHelper;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);

        initView();
        initEvent();

        return view;
    }

    private void initData() {
        PlanListAdapter planListAdapter = new PlanListAdapter(getActivity(), R.layout.cell_plan_list, plans);
        lv_plans.setAdapter(planListAdapter);

        mContext = getActivity();

        dbHelper = DataBaseHelper.GetInstance(mContext, null);
    }

    private void initView() {
        lv_plans = (ListView) view.findViewById(R.id.id_lv_plan_lists);
        img_btn_add_plan = (ImageButton) view.findViewById(R.id.id_img_btn_add_plan);
    }

    private void initEvent() {
        img_btn_add_plan.setOnClickListener(this);
    }

    // 绑定数据
    private void initPlanList() {

        plans.clear();  // 在onResume调用时，在内存中还有值，会导致重复

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DataBaseHelper.TABLE_NAME_PLAN, null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                NockPlan plan = new NockPlan();
                plan.setPlanId(cursor.getInt(cursor.getColumnIndex("id")));
                plan.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                plan.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                plan.setStartDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("start_date"))));
                plan.setEndDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("end_date"))));
                plan.setRecordDays(cursor.getInt(cursor.getColumnIndex("record_days")));
                int flag = cursor.getInt(cursor.getColumnIndex("state"));
                if (flag == 1)
                    plan.setState(true);
                else
                    plan.setState(false);
                plan.setLastDate(DateUtils.getDateFromStr(cursor.getString(cursor.getColumnIndex("last_date"))));

                plans.add(plan);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_btn_add_plan:
                Intent intent = new Intent(getActivity(), AddPlanActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
        initPlanList();
        initPlanListEvent();
    }

    private void initPlanListEvent() {
        lv_plans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, PlanDetailActivity.class);
                intent.putExtra("planId", plans.get(position).getPlanId());
                startActivity(intent);
            }
        });
    }
}
