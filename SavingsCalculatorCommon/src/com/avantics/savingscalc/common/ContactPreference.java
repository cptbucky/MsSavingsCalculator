package com.avantics.savingscalc.common;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by tom on 04/02/14.
 */
public class ContactPreference extends DialogPreference {

    private EditText _entry;
    private Button _lookup;
    private View.OnClickListener _preferenceClickListener;

    private String mValue;

    public ContactPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPersistent(true);

        setDialogLayoutResource(R.layout.preference_contact_lookup);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle(R.string.preference_contact_title);

        super.onPrepareDialogBuilder(builder);
    }

    @Override
    public void onBindDialogView(View view) {
        _entry = (EditText) view.findViewById(R.id.emailEntry);

        setEntryTextValue();

        _lookup = (Button) view.findViewById(R.id.emailLookup);
        _lookup.setOnClickListener(_preferenceClickListener);

        super.onBindDialogView(view);
    }

    private void setEntryTextValue() {
        mValue = getValue(true);

        _entry.setText(mValue);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        super.onSetInitialValue(restoreValue, defaultValue);

        mValue = getValue(restoreValue);
    }

    private String getValue(boolean restoreValue) {
        String mDefault = "", value;

        if (restoreValue) {
            value = shouldPersist() ? getPersistedString(mDefault) : mDefault;
        } else {
            value = mDefault;
        }

        return value;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            String newValue = String.valueOf(_entry.getText());

            persistString(newValue);
        }
    }

    public void setOnLookupClickListener(View.OnClickListener onLookupClickListener) {
        _preferenceClickListener = onLookupClickListener;
    }

    public void UpdateEntry(String newEntry) {
        mValue = newEntry;

        _entry.setText(mValue);
    }
}
