package com.avantics.savingscalc.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.avantics.common.IBindManager;
import com.avantics.common.UiBindingContainer;
import com.avantics.savingscalc.R;
import com.avantics.savingscalc.common.UiBindingManager;
import com.google.ads.AdView;

import java.util.ArrayList;

public class Main extends FragmentActivity implements IBindManager {
    private AdView adView;
    private static UiBindingManager binder;
    ArrayList<UiBindingContainer> containedControls;

    public Main() {
        if (binder == null) {
            binder = new UiBindingManager();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View vw = getLayoutInflater().inflate(R.layout.main_ad, null);

        setContentView(vw);

        containedControls = AttachToView(vw);

//        AdRequest adRequest = new AdRequest();
//        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);         // Emulator
//        adRequest.addTestDevice("015d16897a602010");                // Test Android Device
//
//        // Create the adView
//        adView = new AdView(this, AdSize.BANNER, "ca-app-pub-4197654900696855/6701151722");
//
//        // Lookup your LinearLayout assuming it's been given
//        LinearLayout layout = (LinearLayout)findViewById(R.id.rootLayout);
//
//        // Add the adView to it
//        layout.addView(adView);
//
//        // Initiate a generic request to load it with an ad
//        adView.loadAd(adRequest);
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
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