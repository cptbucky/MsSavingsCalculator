package com.avantics.savingscalc.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avantics.savingscalc.common.R;

public class MobileTabbedFragment extends TabbedFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_swipe_activity, container, false);

        setupView(view);

        return view;
    }
}
