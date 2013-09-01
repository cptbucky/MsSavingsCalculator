package com.avantics.savingscalc.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avantics.savingscalc.common.R;

public class ProposedFragment extends BindableFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_proposed, container, false);

        bindView(view);

        return view;
    }
}

