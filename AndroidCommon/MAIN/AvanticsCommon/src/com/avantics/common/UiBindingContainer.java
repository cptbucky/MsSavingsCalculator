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

//    public UiBindingContainer(TextView editor, NumberFormat format, CalculateInterface callback) {
//        Ctrl = editor;
//        formatter = format;
//        sourceProperty = null;
//
//        defaultToFormattedZero();
//
//        calcInterface = callback;
//
//        if (callback != null){
//            watcher = new CalcWatcher(callback, this);
//
//            Ctrl.addTextChangedListener(watcher);
//        }
//    }

//    public UiBindingContainer(TextView editor, NumberFormat format, CalculateInterface callback, BindableProperty<T> container) {
//        Ctrl = editor;
//        formatter = format;
//        sourceProperty = container;
//
////        defaultToFormattedZero();
//
////        setValue(sourceProperty.getValue());
//
//        calcInterface = callback;
//
//        if (callback != null){
//            watcher = new CalcWatcher(callback, this);
//
//            Ctrl.addTextChangedListener(watcher);
//        }
//    }

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

//    public String getStringValue() {
//        String extractedValue;
//
//        String currentText = Ctrl.getText().toString();
//
//        // hate this code..
//        try {
//            extractedValue = formatter.parse(currentText);
//        } catch (ParseException e) {
//            extractedValue = Helper.getDoubleFrom(currentText);
//        }
//
//        return extractedValue;
//    }

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

//        sourceProperty.setValue(value);

        if (Ctrl.isFocused()) {
            Ctrl.setText(String.valueOf(value));
        } else {
            Ctrl.setText(formatter.format(value));
        }

//        recalculate();
    }

    public void defaultToFormattedZero() {
        if (formatter == null) {
            Ctrl.setText(String.valueOf(0));
        } else {
            Ctrl.setText(formatter.format(0));
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

//    public void recalculate(){
//        if (calcInterface != null){
//            calcInterface.Calculate();
//        }
//    }

    public void updateProperty() {
        Double currentValue = getDoubleValue();

/*
        if (!currentValue.equals(sourceProperty.getValue())) {
*/
        sourceProperty.setValue((T) currentValue);
/*
        }
*/

//        recalculate();
    }

    public void rebindValue() {
        setValue(sourceProperty.getValue());
    }
}