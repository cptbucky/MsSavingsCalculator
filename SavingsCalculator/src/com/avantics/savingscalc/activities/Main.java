package com.avantics.savingscalc.activities;

import android.os.Build;
import android.view.View;

import com.avantics.savingscalc.R;
import com.avantics.savingscalc.common.activities.MainActivity;

public class Main extends MainActivity {

    @Override
    protected View getView(){
        View vw;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            vw = getLayoutInflater().inflate(R.layout.standard_form_ad, null);
        } else {
            vw = getLayoutInflater().inflate(R.layout.main_ad, null);
        }

        return vw;
    }

    @Override
    protected void setCurrentTitle(String title) {
        setTitle(String.format("%s%s",
                getResources().getString(R.string.app_name),
                title.equals("") ? "" : String.format(": %s", title)));
    }
}