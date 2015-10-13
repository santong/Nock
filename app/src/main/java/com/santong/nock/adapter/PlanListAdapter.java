package com.santong.nock.adapter;

import android.content.Context;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.santong.nock.R;
import com.santong.nock.model.NockPlan;
import com.santong.nock.utils.DateUtils;

import java.util.List;

/**
 * Created by santong.
 * At 15/9/27 20:51
 */
public class PlanListAdapter extends ArrayAdapter<NockPlan> {

    private int resourceId;

    public PlanListAdapter(Context context, int resource, List<NockPlan> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NockPlan plan = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.cell_plan_list, null);
            viewHolder = new ViewHolder();

            // 绑定UI
            viewHolder.tv_plan_title = (TextView) view.findViewById(R.id.id_plan_list_cell_info_title);
            viewHolder.tv_plan_date = (TextView) view.findViewById(R.id.id_plan_list_cell_info_date);
            viewHolder.tv_plan_record_days = (TextView) view.findViewById(R.id.id_plan_list_cell_info_record_days);
            viewHolder.tv_plan_des = (TextView) view.findViewById(R.id.id_plan_list_cell_info_des);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        // 想组件赋值
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
        viewHolder.tv_plan_record_days.setText("还剩" + plan.getRecordDays() + "天");

        return view;
    }

    class ViewHolder {
        TextView tv_plan_title;
        TextView tv_plan_record_days;
        TextView tv_plan_date;
        TextView tv_plan_des;
    }
}
