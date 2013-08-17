package com.avantics.savingscalc.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avantics.common.IBindManager;
import com.avantics.common.UiBindingContainer;
import com.avantics.savingscalc.common.R;

import java.util.ArrayList;

public class IncumbentFragment extends Fragment {
    ArrayList<UiBindingContainer> containedControls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_incumbent, container, false);

        IBindManager binder = (IBindManager) getActivity();

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
