package com.avantics.savingscalcpremium.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avantics.savingscalc.common.activities.MainActivity;
import com.avantics.savingscalc.common.ExcelExporter;
import com.avantics.savingscalcpremium.R;

import java.io.File;

public class Main extends MainActivity {

    @Override
    protected View getView(){
        View vw;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            vw = getLayoutInflater().inflate(com.avantics.savingscalc.common.R.layout.standard_form, null);
        } else {
            vw = getLayoutInflater().inflate(com.avantics.savingscalc.common.R.layout.quote_form, null);
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
