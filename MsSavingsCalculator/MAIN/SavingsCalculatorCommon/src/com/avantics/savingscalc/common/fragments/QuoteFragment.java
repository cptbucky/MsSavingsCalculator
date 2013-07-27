package com.avantics.savingscalc.common.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avantics.common.CalcWatcher;
import com.avantics.common.CalculateInterface;
import com.avantics.savingscalc.common.R;
import com.avantics.savingscalc.common.UiBindingManager;

public class QuoteFragment extends Fragment {

    public UiBindingManager binder = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_form, container, false);

        binder = new UiBindingManager();

        binder.AttachToView(view);

        // Inflate the layout for this fragment
        return view;
    }
}
