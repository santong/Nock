package com.santong.nock.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.santong.nock.R;
import com.santong.nock.model.NockNotice;
import com.santong.nock.utils.DataBaseHelper;
import com.santong.nock.utils.DateUtils;

import java.util.List;

/**
 * Created by santong.
 * At 15/12/25 17:57
 */
public class NoticeListAdapter extends ArrayAdapter<NockNotice> {

    private int resoureID;
    private Context mContext;
    private List<NockNotice> noticeList;

    private DataBaseHelper dbHelper;

    public NoticeListAdapter(Context context, int resource, List<NockNotice> objects) {
        super(context, resource, objects);
        mContext = context;
        resoureID = resource;
        noticeList = objects;

        dbHelper = DataBaseHelper.GetInstance(mContext, null);
    }

    @Override
    public NockNotice getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NockNotice nockNotice = noticeList.get(position);
        ViewHolder viewHolder;
        View view;

        if (null == convertView) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cell_notice_list, null);
            viewHolder = new ViewHolder();

            // 绑定UI
            viewHolder.tv_title = (TextView) view.findViewById(R.id.id_cell_tv_notice_title);
            viewHolder.tv_createDate = (TextView) view.findViewById(R.id.id_cell_tv_create_date);
            viewHolder.tv_noticeDate = (TextView) view.findViewById(R.id.id_cell_tv_notice_date);
            viewHolder.rbtn_isComplete = (AppCompatRadioButton) view.findViewById(R.id.id_cell_rb_is_complete);

            viewHolder.rbtn_isComplete.setOnClickListener(new lvButtonOnClickListener());

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.rbtn_isComplete.setTag(position);

        bindValue(viewHolder, nockNotice);

        return view;
    }

    private void bindValue(ViewHolder viewHolder, NockNotice notice) {
        if (!TextUtils.isEmpty(notice.getTitle()))
            viewHolder.tv_title.setText(notice.getTitle());
        if (null != notice.getCreateDate()) {
            viewHolder.tv_createDate.setText(DateUtils.formatDate(notice.getCreateDate()));
        }
        if (null != notice.getNoticeDate()) {
            viewHolder.tv_noticeDate.setText(DateUtils.formatDate(notice.getNoticeDate()));
        }
        viewHolder.rbtn_isComplete.setTag(notice.getNoticeId());
        viewHolder.rbtn_isComplete.setChecked(notice.isComplete());
    }


    class lvButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            NockNotice nockNotice = noticeList.get((int) v.getTag() - 1);

            if (nockNotice.isComplete())
                Toast.makeText(mContext, "这个备忘已经完成了", Toast.LENGTH_SHORT).show();
            else
                nockNotice.setIsComplete(true);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_createDate;
        TextView tv_noticeDate;

        AppCompatRadioButton rbtn_isComplete;
    }
}
