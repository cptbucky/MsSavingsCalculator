package com.avantics.savingscalc.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avantics.savingscalc.common.R;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 02/09/13
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
public class IncumbentHeadlineFragment extends BindableFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_incumbent_headline, container, false);

        bindView(view);

        return view;
    }
}
