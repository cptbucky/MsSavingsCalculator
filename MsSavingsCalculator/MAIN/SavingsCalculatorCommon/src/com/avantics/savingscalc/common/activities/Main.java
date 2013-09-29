package com.avantics.savingscalc.common.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.avantics.common.IBindManager;
import com.avantics.common.UiBindingContainer;
import com.avantics.savingscalc.common.R;
import com.avantics.savingscalc.common.UiBindingManager;

import java.util.ArrayList;

/**
 * Created by tom on 02/06/13.
 */
public class Main extends FragmentActivity implements IBindManager {
    private static UiBindingManager binder;
    ArrayList<UiBindingContainer> containedControls;

    public Main() {
        if (binder == null) {
            binder = new UiBindingManager();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View vw = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            vw = getLayoutInflater().inflate(com.avantics.savingscalc.common.R.layout.standard_form, null);
        } else {
            vw = getLayoutInflater().inflate(R.layout.main, null);
        }

        setContentView(vw);

        containedControls = AttachToView(vw);
    }

    @Override
    public ArrayList<UiBindingContainer> AttachToView(View view) {
        return binder.AttachToView(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        restoreViewState();
    }

    @Override
    public void onStop() {
        super.onStop();

        unbindView();
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
