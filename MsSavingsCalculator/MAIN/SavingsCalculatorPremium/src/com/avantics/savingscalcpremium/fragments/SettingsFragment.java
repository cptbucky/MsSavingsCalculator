package com.avantics.savingscalcpremium.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.avantics.savingscalcpremium.ContactPreference;
import com.avantics.savingscalcpremium.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsFragment extends PreferenceFragment implements
        OnSharedPreferenceChangeListener {

    final static int PICK_SHARE_RECIPIENT = 99;
    final static int PICK_BCC = 100;
    final static int PICK_CC = 101;

    public static final String PREF_BRANDING_VENDOR_NAME = "pref_branding_vendor_name";
    public static final String PREF_SHARE_RECIPIENT = "pref_share_recipient";
    public static final String PREF_SHARE_CC = "pref_share_cc";
    public static final String PREF_SHARE_BCC = "pref_share_bcc";

    private static final String TAG = "SettingsFragment";

    private SharedPreferences sPreferences;

    private EditTextPreference brandingVendorName;

    private ContactPreference shareRecipient;
    private Preference shareCc;
    private Preference shareBcc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.layout.settings);

        // get shared resources for resume
        sPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        // grab the setting preference items
        brandingVendorName = (EditTextPreference) getPreferenceScreen()
                .findPreference(PREF_BRANDING_VENDOR_NAME);

        shareRecipient = (ContactPreference) getPreferenceScreen().findPreference(PREF_SHARE_RECIPIENT);

        shareRecipient.setOnLookupClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);

                startActivityForResult(intent, PICK_SHARE_RECIPIENT);
            }
        });

//        shareRecipient.setOnPreferenceClickListener(getOnPreferenceClickListener(PICK_SHARE_RECIPIENT));

        shareCc = getPreferenceScreen().findPreference(PREF_SHARE_CC);

        shareCc.setOnPreferenceClickListener(getOnPreferenceClickListener(PICK_CC));

        shareBcc = getPreferenceScreen().findPreference(PREF_SHARE_BCC);

        shareBcc.setOnPreferenceClickListener(getOnPreferenceClickListener(PICK_BCC));
    }

    private Preference.OnPreferenceClickListener getOnPreferenceClickListener(final int pickShareRecipient) {
        return new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);

                startActivityForResult(intent, pickShareRecipient);

                return true;
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String email = GetEmail(data);

            switch (requestCode) {
                case PICK_SHARE_RECIPIENT:
                    SetSharedRecipient(email, shareRecipient, PREF_SHARE_RECIPIENT);
                    break;
                case PICK_BCC:
                    SetSharedRecipient(email, shareBcc, PREF_SHARE_BCC);
                    break;
                case PICK_CC:
                    SetSharedRecipient(email, shareCc, PREF_SHARE_CC);
                    break;
            }
        } else {
            Log.w(TAG, "Warning: activity result not ok");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String GetEmail(Intent data) {
        Uri result = data.getData();
        Log.i(TAG, "Got a result: " + result.toString());

        // get the contact id from the Uri
        String id = result.getLastPathSegment();

        final ContentResolver cr = getActivity().getContentResolver();
        final Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email._ID + "=" + id, null, null);

        int nameId = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

        // let's just get the first email
        if (cursor.moveToFirst()) {
            String email = cursor.getString(emailIdx);
            String name = cursor.getString(nameId);

            Log.v(TAG, "Got email: " + email);

            return email;
        } else {
            Log.w(TAG, "No results");
        }

        return "";
    }

    private void SetSharedRecipient(String email, Preference pref, String prefKey) {
        pref.getEditor().putString(prefKey, email).commit();
        pref.setSummary(sPreferences.getString(prefKey, ""));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
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
