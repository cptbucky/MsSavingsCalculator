package com.avantics.savingscalcpremium.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.avantics.common.IBindManager;
import com.avantics.common.UiBindingContainer;
import com.avantics.savingscalc.common.DatabaseHelper;
import com.avantics.savingscalc.common.UiBindingManager;
import com.avantics.savingscalc.common.activities.MainActivity;
import com.avantics.savingscalc.common.entities.Quote;
import com.avantics.savingscalc.common.ConfirmDialogFragment;
import com.avantics.savingscalc.common.ConfirmationDialogHandler;
import com.avantics.savingscalc.common.DeleteQuoteRequestHandler;
import com.avantics.savingscalcpremium.ExcelExporter;
import com.avantics.savingscalc.common.LoadListItem;
import com.avantics.savingscalc.common.LoadListItemAdapter;
import com.avantics.savingscalcpremium.R;
import com.avantics.savingscalcpremium.fragments.SettingsFragment;

import java.io.File;
import java.util.ArrayList;

public class Main extends MainActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        appMenu = menu;

        setLoadVisibility();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        clearFocusAndKeyboard();

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_send:
                sendQuote();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendQuote() {
        String filePath;

        if (binder.currentQuote.Name.getValue().equals("")) {
            filePath = String.format("%s/MsSavingsCalculator_Quote.xls", Environment.getExternalStorageDirectory());
        } else {
            filePath = String.format("%s/MsSavingsCalculator_Quote - %s.xls", Environment.getExternalStorageDirectory(), binder.currentQuote.Name.getValue());
        }

        ExcelExporter xlEngine = new ExcelExporter(filePath);

        xlEngine.CreateQuoteWorkSheet(binder.currentQuote, getResources());

        SendEmailAttachWorksheet(filePath);
    }

    private void SendEmailAttachWorksheet(String filePath) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        File file = new File(filePath);

        Uri fileUri = Uri.fromFile(file);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");

        String[] recipient = new String[]{preferences.getString(SettingsFragment.PREF_SHARE_RECIPIENT, String.valueOf(FragmentActivity.MODE_PRIVATE))};
        if (recipient.length == 1)
            i.putExtra(Intent.EXTRA_EMAIL, recipient);

        String[] cc = new String[]{preferences.getString(SettingsFragment.PREF_SHARE_CC, String.valueOf(FragmentActivity.MODE_PRIVATE))};
        if (cc.length == 1)
            i.putExtra(Intent.EXTRA_CC, cc);

        String[] bcc = new String[]{preferences.getString(SettingsFragment.PREF_SHARE_BCC, String.valueOf(FragmentActivity.MODE_PRIVATE))};
        if (bcc.length == 1)
            i.putExtra(Intent.EXTRA_BCC, bcc);

        i.putExtra(Intent.EXTRA_SUBJECT, "MsSavingsCalculator Quote");
        i.putExtra(Intent.EXTRA_TEXT, "Please see attached for the MsSavingsCalculator export of a client quote.");
        i.putExtra(Intent.EXTRA_STREAM, fileUri);

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            // Toast.makeText(MyActivity.this,
            // "There are no email clients installed.",
            // Toast.LENGTH_SHORT).show();
        }
    }

    public void setSelectedQuote(int which) {
        String quoteName = availableQuoteNames.valueAt(which);
        Quote requestedQuote = dbHelper.getQuote(quoteName);

        binder.setSelectedQuote(requestedQuote);

        setCurrentTitle(quoteName);
    }

    @Override
    protected void setCurrentTitle(String title) {
        setTitle(String.format("%s%s",
                getResources().getString(R.string.app_name),
                title.equals("") ? "" : String.format(": %s", title)));
    }
}
