package com.avantics.savingscalc.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avantics.common.UiBindingContainer;
import com.avantics.savingscalc.common.R;
import com.avantics.savingscalc.common.UiBindingManager;

import java.util.ArrayList;

public class QuoteFragment extends Fragment {
    ArrayList<UiBindingContainer> containedControls;

    public UiBindingManager binder = null;

    public QuoteFragment() {
        binder = new UiBindingManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_form, container, false);

        containedControls = binder.AttachToView(view);

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle b) {
        // set values of all controls
        for (int i = 0; i < containedControls.size(); i++) {
            containedControls.get(i).rebindValue();
        }

        super.onViewStateRestored(b);
    }
}
