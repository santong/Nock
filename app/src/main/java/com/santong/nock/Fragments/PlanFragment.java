package com.santong.nock.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santong.nock.R;

/**
 * Created by santong.
 * At 15/9/27 18:57
 */
public class PlanFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        return view;
    }
}
