package com.avantics.savingscalcpremium.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import com.avantics.savingscalcpremium.R;

public class SupportSettingsActivity extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    //	public static final String PREF_BRANDING_ICON = "pref_branding_icon";
    public static final String PREF_BRANDING_VENDOR_NAME = "pref_branding_vendor_name";
    public static final String PREF_SHARE_RECIPIENT = "pref_share_recipient";
    public static final String PREF_SHARE_CC = "pref_share_cc";
    public static final String PREF_SHARE_BCC = "pref_share_bcc";
//	public static final String PREF_QUOTE_DEFAULT = "pref_quote_default";

    private SharedPreferences sPreferences;

    //	private EditTextPreference brandingIcon;
    private EditTextPreference brandingVendorName;
    private EditTextPreference shareRecipient;
    private EditTextPreference shareCc;
    private EditTextPreference shareBcc;
//	private EditTextPreference quoteDefault;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.layout.settings);

        // get shared resources for resume
        sPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // grab the setting preference items
//		brandingIcon = (EditTextPreference) getPreferenceScreen()
//				.findPreference(PREF_BRANDING_ICON);

        brandingVendorName = (EditTextPreference) getPreferenceScreen()
                .findPreference(PREF_BRANDING_VENDOR_NAME);

        shareRecipient = (EditTextPreference) getPreferenceScreen()
                .findPreference(PREF_SHARE_RECIPIENT);

        shareCc = (EditTextPreference) getPreferenceScreen().findPreference(
                PREF_SHARE_CC);

        shareBcc = (EditTextPreference) getPreferenceScreen().findPreference(
                PREF_SHARE_BCC);

//		quoteDefault = (EditTextPreference) getPreferenceScreen()
//				.findPreference(PREF_QUOTE_DEFAULT);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(PREF_SHARE_RECIPIENT)) {
            shareRecipient.setSummary(sharedPreferences.getString(key, ""));
//		} else if (key.equals(PREF_BRANDING_ICON)) {
//			brandingIcon.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(PREF_BRANDING_VENDOR_NAME)) {
            brandingVendorName.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(PREF_SHARE_CC)) {
            shareCc.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(PREF_SHARE_BCC)) {
            shareBcc.setSummary(sharedPreferences.getString(key, ""));
//		} else if (key.equals(PREF_QUOTE_DEFAULT)) {
//			quoteDefault.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // set preference summaries
//		brandingIcon.setSummary(sPreferences.getString(PREF_BRANDING_ICON,
//				"").equals("") ? getResources().getText(
//				R.string.pref_branding_icon_summ) : sPreferences.getString(
//				PREF_BRANDING_ICON, ""));

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

//		quoteDefault.setSummary(sPreferences.getString(PREF_QUOTE_DEFAULT,
//				"").equals("") ? getResources().getText(
//				R.string.pref_quote_default_summ) : sPreferences.getString(
//				PREF_QUOTE_DEFAULT, ""));

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

