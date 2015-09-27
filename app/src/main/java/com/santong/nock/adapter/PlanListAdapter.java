package com.santong.nock.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by santong.
 * At 15/9/27 20:51
 */
public class PlanListAdapter extends ArrayAdapter {
    public PlanListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }
}
