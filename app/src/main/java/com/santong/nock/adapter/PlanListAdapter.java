package com.santong.nock.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.santong.nock.R;
import com.santong.nock.activitys.PlanReportActivity;
import com.santong.nock.model.NockPlan;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by santong.
 * At 15/9/27 20:51
 */
public class PlanListAdapter extends ArrayAdapter<NockPlan> {

    private int resourceId;

    private View view;

    private List<NockPlan> planList;

    private DataBaseHelper dbHelper;

    private Context mContext;

    public PlanListAdapter(Context context, int resource, List<NockPlan> objects) {
        super(context, resource, objects);
        mContext = context;
        resourceId = resource;
        planList = objects;
        dbHelper = DataBaseHelper.GetInstance(mContext, null);
    }

    @Override
    public NockPlan getItem(int position) {
        return planList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    //  TODO: 尝试解决getView重复调用问题
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NockPlan plan = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.cell_plan_list, null);
            viewHolder = new ViewHolder();

            // 绑定UI
            viewHolder.tv_plan_title = (TextView) view.findViewById(R.id.id_plan_list_cell_info_title);
            viewHolder.tv_plan_date = (TextView) view.findViewById(R.id.id_plan_list_cell_info_date);
            viewHolder.tv_plan_record_days = (TextView) view.findViewById(R.id.id_plan_list_cell_info_record_days);
            viewHolder.tv_plan_des = (TextView) view.findViewById(R.id.id_plan_list_cell_info_des);

            viewHolder.ll_plan_cell_container = (LinearLayout) view.findViewById(R.id.id_plan_list_cell_container);

            viewHolder.btn_record = (Button) view.findViewById(R.id.id_plan_list_cell_btn_record);

            viewHolder.btn_record.setOnClickListener(new lvButtonListener(position, viewHolder));

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        // 向组件赋值
        if (!TextUtils.isEmpty(plan.getTitle()))
            viewHolder.tv_plan_title.setText(plan.getTitle());
        else
            viewHolder.tv_plan_title.setText("未添加题目");
        if (!TextUtils.isEmpty(plan.getDescription()))
            viewHolder.tv_plan_des.setText(plan.getDescription() + "");
        else
            viewHolder.tv_plan_des.setText("未添加描述");
        if (plan.getStartDate() == null && plan.getEndDate() == null)
            viewHolder.tv_plan_date.setText("未添加日期");
        else {
            viewHolder.tv_plan_date.setText(DateUtils.formatDate(plan.getStartDate()) + "~" + DateUtils.formatDate(plan.getEndDate()));
        }

        // 初始化按钮状态
        viewHolder.btn_record.setEnabled(true);
        viewHolder.btn_record.setBackgroundResource(R.color.green);
        viewHolder.ll_plan_cell_container.setBackgroundResource(R.color.white);

        viewHolder.tv_plan_record_days.setText("共" + (DateUtils.getDaysFrom2Date(plan.getStartDate(), plan.getEndDate()) + 1) + "天");

        // 对当前状态进行操作
        if (plan.isFinished()) {
            viewHolder.btn_record.setText("计划报告");
            viewHolder.btn_record.setBackgroundResource(R.color.deep_red);
            viewHolder.ll_plan_cell_container.setBackgroundResource(R.color.bg_grey);
            viewHolder.tv_plan_record_days.setText("完成啦!!");
        } else {
            viewHolder.btn_record.setText("已打卡" + plan.getRecordDays() + "天");
        }

        return view;
    }


    class ViewHolder {
        TextView tv_plan_title;
        TextView tv_plan_record_days;
        TextView tv_plan_date;
        TextView tv_plan_des;

        Button btn_record;

        LinearLayout ll_plan_cell_container;
    }

    class lvButtonListener implements View.OnClickListener {

        private int position;
        private ViewHolder holder;

        lvButtonListener(int pos, ViewHolder viewHolder) {
            position = pos;
            holder = viewHolder;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == holder.btn_record.getId()) {
                NockPlan plan = planList.get(position);
                if (plan.isFinished()) {
                    PlanReport(plan);
                } else
                    PlanRecorded(plan);
                notifyDataSetChanged();
            }
        }
    }

    private void PlanReport(NockPlan plan) {
        Intent intent = new Intent(mContext, PlanReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("plan", plan);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void PlanRecorded(NockPlan plan) {
        int recordDays = plan.getRecordDays();
        Date lastDate = plan.getLastDate();

        Log.e("recordDays", "" + recordDays);

        if (null != lastDate && !TextUtils.isEmpty(lastDate + "") && DateUtils.isToday(lastDate)) {
            Toast.makeText(mContext, "今天已经打过卡了。", Toast.LENGTH_SHORT).show();
        } else {
            recordDays = recordDays + 1;
            plan.setRecordDays(recordDays);
            plan.setLastDate(DateUtils.getCurrentDate());
        }

        if (recordDays >= (DateUtils.getDaysFrom2Date(plan.getStartDate(), plan.getEndDate()) + 1)) {
            plan.setState(true);
        }

        dbHelper.UpdatePlan(plan);
    }

}
