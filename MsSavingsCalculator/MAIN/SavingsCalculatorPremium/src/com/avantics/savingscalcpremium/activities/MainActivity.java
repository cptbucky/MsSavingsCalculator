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
import com.avantics.savingscalc.common.UiBindingManager;
import com.avantics.savingscalc.common.entities.Quote;
import com.avantics.savingscalcpremium.ConfirmDialogFragment;
import com.avantics.savingscalcpremium.ConfirmationDialogHandler;
import com.avantics.savingscalcpremium.DatabaseHelper;
import com.avantics.savingscalcpremium.DeleteQuoteRequestHandler;
import com.avantics.savingscalcpremium.ExcelExporter;
import com.avantics.savingscalcpremium.LoadListItem;
import com.avantics.savingscalcpremium.LoadListItemAdapter;
import com.avantics.savingscalcpremium.R;
import com.avantics.savingscalcpremium.fragments.SettingsFragment;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements IBindManager {
    private static final String HeaderTextKey = "HEADER_TEXT";

    private static UiBindingManager binder;
    ArrayList<UiBindingContainer> containedControls;

    private DatabaseHelper dbHelper = null;
    private SparseArray<String> availableQuoteNames;

    private Menu appMenu;

    public MainActivity() {
        if (binder == null) {
            binder = new UiBindingManager();
        }
    }

    @Override
    public ArrayList<UiBindingContainer> AttachToView(View view) {
        return binder.AttachToView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View vw = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            vw = getLayoutInflater().inflate(com.avantics.savingscalc.common.R.layout.standard_form, null);
        } else {
            vw = getLayoutInflater().inflate(R.layout.premium_quote_form, null);
        }

        setContentView(vw);

        containedControls = AttachToView(vw);

        PreferenceManager.setDefaultValues(this, R.layout.settings, false);

        dbHelper = new DatabaseHelper(this, 6);

        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(HeaderTextKey);
            setCurrentTitle(savedText);
        }
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
            case R.id.action_save:
                saveQuote(new ConfirmationDialogHandler() {
                    @Override
                    public void callback() {
                    }
                });

                return true;
            case R.id.action_load:
                loadQuote();

                return true;
            case R.id.action_resetEntry:
                resetQuote();

                return true;
            case R.id.action_send:
                sendQuote();

                return true;
            case R.id.action_settings:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    Intent i = new Intent(this, SupportSettingsActivity.class);
                    startActivityForResult(i, 1);
                } else {
                    Intent i = new Intent(this, SettingsActivity.class);
                    startActivityForResult(i, 1);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(HeaderTextKey, binder.currentQuote.Name.getValue());
    }

    private void setLoadVisibility() {
        boolean saveCount = dbHelper.getAvailableQuoteNames().size() > 0;

        MenuItem item = appMenu.findItem(R.id.action_load);
        item.setVisible(saveCount);
    }

    private void clearFocusAndKeyboard() {
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        rootLayout.requestFocus();

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void resetQuote() {
        binder.resetQuote();
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

    private void loadQuote() {
        availableQuoteNames = dbHelper.getAvailableQuoteNames();

        if (availableQuoteNames.size() > 0) {
            final Dialog manageDialog = new Dialog(this);

            manageDialog.setContentView(R.layout.load_dialog);
            manageDialog.setTitle(R.string.load_quote_dialog_title);

            ListView listView1;

            final ArrayList<LoadListItem> load_items = new ArrayList<LoadListItem>();

            for (int i = 0; i < availableQuoteNames.size(); i++) {
                load_items
                        .add(new LoadListItem(availableQuoteNames.valueAt(i)));
            }

            final LoadListItemAdapter adapter = new LoadListItemAdapter(this,
                    R.layout.listview_item_row, load_items);

            adapter.setOnDeleteQuoteRequested(new DeleteQuoteRequestHandler() {

                @Override
                public void callback(LoadListItem arg2) {
                    final String title = arg2.title;

                    ConfirmDialogFragment confirmation = new ConfirmDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("titleString",
                            String.format("Delete Quote %s?", title));
                    args.putString("confirmString", "Delete");
                    args.putString("cancelString", "Cancel");
                    confirmation.setArguments(args);
                    confirmation.setOnConfirmed(new ConfirmationDialogHandler() {

                        @Override
                        public void callback() {
                            Context context = getApplicationContext();
                            CharSequence text = String.format(
                                    "%s deleted", title);
                            int duration = Toast.LENGTH_SHORT;

                            dbHelper.deleteQuote(title);

                            if (binder.getSelectedQuote().Name.getValue()
                                    .equals(title)) {
                                binder.resetQuote();

                                setTitle(getResources().getString(
                                        R.string.app_name));
                            }

                            availableQuoteNames = dbHelper
                                    .getAvailableQuoteNames();

                            load_items.clear();

                            for (int i = 0; i < availableQuoteNames
                                    .size(); i++) {
                                load_items
                                        .add(new LoadListItem(
                                                availableQuoteNames
                                                        .valueAt(i)));
                            }

                            if (availableQuoteNames.size() == 0) {
                                manageDialog.dismiss();
                                setLoadVisibility();
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    });

                    confirmation.show(getSupportFragmentManager(), "confirmDelete");
                }
            });

            listView1 = (ListView) manageDialog.findViewById(R.id.listView1);

            listView1.setAdapter(adapter);

            listView1.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    final int title = arg2;

                    if (binder.getSelectedQuote().HasChanged()) {
                        ConfirmDialogFragment confirmation = new ConfirmDialogFragment();
                        Bundle args = new Bundle();
                        args.putString("titleString", "Save Previous Quote?");
                        args.putString("confirmString", "Yes");
                        args.putString("cancelString", "No");
                        confirmation.setArguments(args);
                        confirmation.setOnConfirmed(new ConfirmationDialogHandler() {

                            @Override
                            public void callback() {
                                saveQuote(new ConfirmationDialogHandler() {

                                    @Override
                                    public void callback() {
                                        setSelectedQuote(title);

                                        manageDialog.dismiss();
                                    }
                                });
                            }
                        });
                        confirmation.setOnCanceled(new ConfirmationDialogHandler() {

                            @Override
                            public void callback() {
                                setSelectedQuote(title);

                                manageDialog.dismiss();
                            }
                        });

                        confirmation.show(getSupportFragmentManager(), "savePreviousQuote");
                    } else {
                        setSelectedQuote(arg2);

                        manageDialog.dismiss();
                    }
                }
            });

            manageDialog.show();
        }
    }

    private void saveQuote(final ConfirmationDialogHandler confirmationDialogHandler) {
        LayoutInflater factory = LayoutInflater.from(MainActivity.this);

        final View alertDialogView = factory.inflate(R.layout.save_quote_view, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Save Quote As..")
                .setView(alertDialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // Do nothing.
                            }
                        });

        final AlertDialog saveDialog = builder.create();

        final ConfirmDialogFragment confirmation = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putString("titleString", "Overwrite Quote?");
        args.putString("confirmString", "Overwrite");
        args.putString("cancelString", "Cancel");
        confirmation.setArguments(args);
        confirmation.setOnConfirmed(new ConfirmationDialogHandler() {

            @Override
            public void callback() {
                EditText saveNameSource = (EditText) alertDialogView
                        .findViewById(R.id.txtQuoteName);

                String quoteName = String.format("%s", saveNameSource.getText()); // currently
                // no
                // validation!!

                Quote quote = binder.getSelectedQuote();
                quote.Name.setValue(quoteName);

                dbHelper.updateQuote(quote);

                setCurrentTitle(quote.Name.getValue());

                saveDialog.dismiss();

                confirmationDialogHandler.callback();

                Context context = getApplicationContext();
                CharSequence text = String.format("%s saved", quoteName);
                int duration = Toast.LENGTH_SHORT;

                setLoadVisibility();

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        saveDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        saveDialog.show();

        Button btn = saveDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText saveNameSource = (EditText) alertDialogView
                        .findViewById(R.id.txtQuoteName);

                String quoteName = String
                        .format("%s", saveNameSource.getText()); // currently no
                // validation!!

                Boolean wantToCloseDialog = !dbHelper.quoteExists(quoteName);

                if (wantToCloseDialog) {
                    Quote quote = binder.getSelectedQuote();
                    quote.Name.setValue(quoteName);

                    dbHelper.addQuote(quote);

                    setCurrentTitle(quote.Name.getValue());

                    saveDialog.dismiss();

                    confirmationDialogHandler.callback();

                    Context context = getApplicationContext();
                    CharSequence text = String.format("%s saved", quoteName);
                    int duration = Toast.LENGTH_SHORT;

                    setLoadVisibility();

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    confirmation.show(getSupportFragmentManager(), "confirmOverwrite");
                }
            }
        };

        btn.setOnClickListener(listener);
    }

    public void setSelectedQuote(int which) {
        String quoteName = availableQuoteNames.valueAt(which);
        Quote requestedQuote = dbHelper.getQuote(quoteName);

        binder.setSelectedQuote(requestedQuote);

        setCurrentTitle(quoteName);
    }

    private void setCurrentTitle(String title) {
        setTitle(String.format("%s%s",
                getResources().getString(R.string.app_name),
                title.equals("") ? "" : String.format(": %s", title)));
    }
}
