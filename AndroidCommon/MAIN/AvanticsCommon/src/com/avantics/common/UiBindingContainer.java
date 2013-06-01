package com.avantics.common;

import java.text.NumberFormat;
import java.text.ParseException;


import android.widget.TextView;

public class UiBindingContainer {
	public TextView Ctrl = null;
	private NumberFormat formatter = null;

	public UiBindingContainer(TextView editor, NumberFormat format) {
		Ctrl = editor;
		formatter = format;
		
		defaultToFormattedZero();
	}

	public Double getValue() {
		Double rtrn;

		String currentText = Ctrl.getText().toString();

		// hate this code..
		try {
			rtrn = formatter.parse(currentText).doubleValue();
		} catch (ParseException e) {
			rtrn = Helper.getDoubleFrom(currentText);
		}

		return rtrn;
	}

	public void setValue(Double value) {
		if (Ctrl.isFocused()) {
			Ctrl.setText(String.valueOf(value));
		} else {
			Ctrl.setText(formatter.format(value));
		}
	}

	public void defaultToFormattedZero() {
		setValue(0.00);
	}
}