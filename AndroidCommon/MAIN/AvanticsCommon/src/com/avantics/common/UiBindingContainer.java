package com.avantics.common;

import android.text.TextWatcher;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;

public class UiBindingContainer<T> {
    private final CalculateInterface calcInterface;
    public TextView Ctrl = null;
	private NumberFormat formatter = null;
    private TextWatcher watcher = null;

    private BindableProperty<T> sourceProperty;

    public UiBindingContainer(TextView editor, NumberFormat format, CalculateInterface callback) {
        Ctrl = editor;
        formatter = format;
        sourceProperty = null;

        defaultToFormattedZero();

        calcInterface = callback;

        if (callback != null){
            watcher = new CalcWatcher(callback, this);

            Ctrl.addTextChangedListener(watcher);
        }
    }

    public UiBindingContainer(TextView editor, NumberFormat format, CalculateInterface callback, BindableProperty<T> container) {
        Ctrl = editor;
        formatter = format;
        sourceProperty = container;

        defaultToFormattedZero();

        calcInterface = callback;

        if (callback != null){
            watcher = new CalcWatcher(callback, this);

            Ctrl.addTextChangedListener(watcher);
        }
    }

	public double getValue() {
		double extractedValue;

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
        T currentValue = sourceProperty.getValue();

        if (value.equals(currentValue))
            return;

        sourceProperty.setValue(value);

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

    public void suspendChangeNotification(){
        if (watcher != null){
            Ctrl.removeTextChangedListener(watcher);
        }
    }

    public void resumeChangeNotification(){
        if (watcher != null){
            Ctrl.addTextChangedListener(watcher);
        }
    }

    public void recalculate(){
        if (calcInterface != null){
            calcInterface.Calculate();
        }
    }
}