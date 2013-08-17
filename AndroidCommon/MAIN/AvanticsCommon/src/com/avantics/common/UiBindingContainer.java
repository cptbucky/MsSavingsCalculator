package com.avantics.common;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;

public class UiBindingContainer<T> {
    //    private final CalculateInterface calcInterface;
    public TextView Ctrl = null;
    public BindableProperty<T> sourceProperty;
    private NumberFormat formatter = null;
    private TextWatcher watcher = null;

    public UiBindingContainer(TextView wrappedEditor, NumberFormat format, BindableProperty property) {
        Ctrl = wrappedEditor;
        formatter = format;
        sourceProperty = property;

        Ctrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateProperty();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public Double getDoubleValue() {
        Double extractedValue;

        String currentText = Ctrl.getText().toString();

        // hate this code..
        try {
            extractedValue = formatter.parse(currentText).doubleValue();
        } catch (ParseException e) {
            extractedValue = Helper.getDoubleFrom(currentText);
        }

        return extractedValue;
    }

    public void setValue(T value) {
        T currentValue = (T) getDoubleValue();

        if (value.equals(currentValue))
            return;

        if (Ctrl.isFocused()) {
            Ctrl.setText(String.valueOf(value));
        } else {
            Ctrl.setText(formatter.format(value));
        }

    }

    public void suspendChangeNotification() {
        if (watcher != null) {
            Ctrl.removeTextChangedListener(watcher);
        }
    }

    public void resumeChangeNotification() {
        if (watcher != null) {
            Ctrl.addTextChangedListener(watcher);
        }
    }

    public void updateProperty() {
        Double currentValue = getDoubleValue();

        sourceProperty.setValue((T) currentValue);
    }

    public void rebindValue() {
        setValue(sourceProperty.getValue());
    }
}