package com.avantics.savingscalc.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.avantics.common.IBindManager;
import com.avantics.common.UiBindingContainer;

import java.util.ArrayList;

public class BindableFragment extends Fragment {
    ArrayList<UiBindingContainer> containedControls;

    protected void bindView(View view) {
        IBindManager binder = (IBindManager) getActivity();

        containedControls = binder.AttachToView(view);
    }

    @Override
    public void onViewStateRestored(Bundle b) {
        restoreViewState();

        super.onViewStateRestored(b);
    }

    @Override
    public void onDestroyView() {
        unbindView();

        super.onDestroyView();
    }

    private void restoreViewState() {
        // set values of all controls
        for (int i = 0; i < containedControls.size(); i++) {
            containedControls.get(i).rebindValue();
        }
    }

    private void unbindView() {
        // if any controls exist in lists of listeners ensure they are removed
        for (int i = 0; i < containedControls.size(); i++) {
            // this is just plain wrong.. really getting bad now..
            containedControls.get(i).sourceProperty.removeListener(containedControls.get(i));
        }
    }
}
