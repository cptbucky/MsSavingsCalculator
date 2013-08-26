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

public class SavingsFragment extends Fragment {
    ArrayList<UiBindingContainer> containedControls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_savings, container, false);

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

    @Override
    public void onDestroyView() {
        // if any controls exist in lists of listeners ensure they are removed
        for (int i = 0; i < containedControls.size(); i++) {
            // this is just plain wrong.. really getting bad now..
            containedControls.get(i).sourceProperty.removeListener(containedControls.get(i));
        }

        super.onDestroyView();
    }
}
