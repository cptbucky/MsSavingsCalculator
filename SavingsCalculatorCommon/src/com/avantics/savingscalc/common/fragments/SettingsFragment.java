package com.avantics.savingscalc.common.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avantics.savingscalc.common.ChooseEmailItemAdapter;
import com.avantics.savingscalc.common.ContactPreference;
import com.avantics.savingscalc.common.LoadListItem;
import com.avantics.savingscalc.common.R;

import java.util.ArrayList;

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
    private ContactPreference shareCc;
    private ContactPreference shareBcc;

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

        shareRecipient.setOnLookupClickListener(getOnLookupClickListener(PICK_SHARE_RECIPIENT));

        shareCc = (ContactPreference) getPreferenceScreen().findPreference(PREF_SHARE_CC);

        shareCc.setOnLookupClickListener(getOnLookupClickListener(PICK_CC));

        shareBcc = (ContactPreference) getPreferenceScreen().findPreference(PREF_SHARE_BCC);

        shareBcc.setOnLookupClickListener(getOnLookupClickListener(PICK_BCC));
    }

    private View.OnClickListener getOnLookupClickListener(final int prefKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(intent, prefKey);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            setEmailFromContact(data, requestCode);
        } else {
            Log.w(TAG, "Warning: activity result not ok");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setEmailFromContact(Intent data, int requestCode) {
        Uri result = data.getData();
        Log.i(TAG, "Got a result: " + result.toString());

        // get the contact id from the Uri
        String id = result.getLastPathSegment();

        final ContentResolver cr = getActivity().getContentResolver();

        Cursor emailCur = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);

        if (emailCur.getCount() > 1) {
            pickWhichEmailAddress(emailCur, requestCode);
        } else if (emailCur.moveToFirst()) {
            String email = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

            setEmailPreference(requestCode, email);
        } else {
            setEmailPreference(requestCode, "");
        }

        emailCur.close();
    }

    private void pickWhichEmailAddress(Cursor emailCur, final int requestCode) {
        final String[] email = {""};
        final ArrayList<LoadListItem> emails = new ArrayList<LoadListItem>();

        while (emailCur.moveToNext()) {
            // This would allow you get several email addresses
            // if the email addresses were stored in an array
            emails.add(new LoadListItem(emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))));
        }

        if (emails.size() > 0) {
            final Dialog manageDialog = new Dialog(getActivity());

            manageDialog.setContentView(R.layout.listview_dialog);
            manageDialog.setTitle(R.string.choose_email_heading);

            ListView listView1;

            final ChooseEmailItemAdapter adapter = new ChooseEmailItemAdapter(getActivity(),
                    R.layout.choose_item_row, emails);

            listView1 = (ListView) manageDialog.findViewById(R.id.listView1);

            listView1.setAdapter(adapter);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    email[0] = emails.get(arg2).title;

                    setEmailPreference(requestCode, email[0]);

                    manageDialog.dismiss();
                }
            });

            manageDialog.show();
        }
    }

    private void setEmailPreference(int requestCode, String email) {
        switch (requestCode) {
            case PICK_SHARE_RECIPIENT:
                shareRecipient.UpdateEntry(email);

                break;
            case PICK_BCC:
                shareBcc.UpdateEntry(email);

                break;
            case PICK_CC:
                shareCc.UpdateEntry(email);

                break;
        }
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
