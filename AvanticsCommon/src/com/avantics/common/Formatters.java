package com.avantics.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.text.InputFilter;
import android.widget.EditText;

public class Formatters {
	public NumberFormat CURRENCY_FORMATTER;
	public NumberFormat PERCENTAGE_FORMATTER;
	public NumberFormat DECIMAL_FORMATTER;

	public Formatters() {
		CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
		
		PERCENTAGE_FORMATTER = NumberFormat.getPercentInstance();
		PERCENTAGE_FORMATTER.setMinimumFractionDigits(2);;
		
		DECIMAL_FORMATTER = DecimalFormat.getNumberInstance();
		DECIMAL_FORMATTER.setMinimumFractionDigits(2);
	}
	
	public EditText getCurrencyEditor(EditText editor) {
		return getFormattedEditText(editor, CURRENCY_FORMATTER);
	}
	
	public EditText getPercentageEditor(EditText editor) {
		return getFormattedEditText(editor, PERCENTAGE_FORMATTER);
	}
	
	public EditText getDecimalEditor(EditText editor) {
		return getFormattedEditText(editor, DECIMAL_FORMATTER);
	}
	
	private EditText getFormattedEditText(EditText editor, NumberFormat formatter) {
		// set the default by code to catch device setup
		editor.setText(formatter.format(0.00));
		
		editor.setFilters(new InputFilter[] { new NumericRangeFilter(formatter) });
		editor.setOnFocusChangeListener(new AmountOnFocusChangeListener(formatter));
		
		return editor;
	}
}