package com.avantics.savingscalc.common.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.avantics.savingscalc.common.R;

public class SupportSettingsActivity extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PREF_BRANDING_VENDOR_NAME = "pref_branding_vendor_name";
    public static final String PREF_SHARE_RECIPIENT = "pref_share_recipient";
    public static final String PREF_SHARE_CC = "pref_share_cc";
    public static final String PREF_SHARE_BCC = "pref_share_bcc";

    private SharedPreferences sPreferences;

    private EditTextPreference brandingVendorName;
    private EditTextPreference shareRecipient;
    private EditTextPreference shareCc;
    private EditTextPreference shareBcc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.layout.settings);

        // get shared resources for resume
        sPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // grab the setting preference items
        brandingVendorName = (EditTextPreference) getPreferenceScreen()
                .findPreference(PREF_BRANDING_VENDOR_NAME);

        shareRecipient = (EditTextPreference) getPreferenceScreen()
                .findPreference(PREF_SHARE_RECIPIENT);

        shareCc = (EditTextPreference) getPreferenceScreen().findPreference(
                PREF_SHARE_CC);

        shareBcc = (EditTextPreference) getPreferenceScreen().findPreference(
                PREF_SHARE_BCC);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(PREF_SHARE_RECIPIENT)) {
            shareRecipient.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(PREF_BRANDING_VENDOR_NAME)) {
            brandingVendorName.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(PREF_SHARE_CC)) {
            shareCc.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(PREF_SHARE_BCC)) {
            shareBcc.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // set preference summaries
        brandingVendorName.setSummary(sPreferences.getString(PREF_BRANDING_VENDOR_NAME,
                "").equals("") ? getResources().getText(
                R.string.pref_branding_vendor_name_summ) : sPreferences.getString(
                PREF_BRANDING_VENDOR_NAME, ""));

        shareRecipient.setSummary(sPreferences.getString(PREF_SHARE_RECIPIENT,
                "").equals("") ? getResources().getText(
                R.string.pref_share_recipient_summ) : sPreferences.getString(
                PREF_SHARE_RECIPIENT, ""));

        shareCc.setSummary(sPreferences.getString(PREF_SHARE_CC,
                "").equals("") ? getResources().getText(
                R.string.pref_share_cc_summ) : sPreferences.getString(
                PREF_SHARE_CC, ""));

        shareBcc.setSummary(sPreferences.getString(PREF_SHARE_BCC,
                "").equals("") ? getResources().getText(
                R.string.pref_share_bcc_summ) : sPreferences.getString(
                PREF_SHARE_BCC, ""));

        // register for changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}

