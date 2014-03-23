package com.avantics.savingscalc.common.email;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.avantics.savingscalc.common.fragments.SettingsFragment;

import java.io.File;

public class EmailDispatcher implements IEmailDispatcher {

    private final IAttachmentSource _source;
    private final SharedPreferences _preferences;

    public EmailDispatcher(SharedPreferences preferences, IAttachmentSource source) {
        this._preferences = preferences;
        this._source = source;
    }

    @Override
    public Intent GetEmailIntent() {
        File file = new File(_source.GetAttachmentSource());

        Uri fileUri = Uri.fromFile(file);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");

        AddRecipients(this._preferences, i);

        i.putExtra(Intent.EXTRA_SUBJECT, "MsSavingsCalculator Quote");
        i.putExtra(Intent.EXTRA_TEXT, "Please see attached for the MsSavingsCalculator export of a client quote.");
        i.putExtra(Intent.EXTRA_STREAM, fileUri);

        return Intent.createChooser(i, "Send mail...");
    }

    private void AddRecipients(SharedPreferences preferences, Intent i) {
        AddRecipient(preferences, i, SettingsFragment.PREF_SHARE_RECIPIENT, Intent.EXTRA_EMAIL);
        AddRecipient(preferences, i, SettingsFragment.PREF_SHARE_CC, Intent.EXTRA_CC);
        AddRecipient(preferences, i, SettingsFragment.PREF_SHARE_BCC, Intent.EXTRA_BCC);
    }

    private void AddRecipient(SharedPreferences preferences, Intent i, String prefShareRecipient, String extraEmail) {
        String recipient = preferences.getString(prefShareRecipient, String.valueOf(FragmentActivity.MODE_PRIVATE));

        if (preferences.contains(prefShareRecipient))
        {
            i.putExtra(extraEmail, new String[]{recipient});
        }
    }
}
