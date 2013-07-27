package com.avantics.common;

import android.text.Editable;
import android.text.TextWatcher;

public class CalcWatcher implements TextWatcher {
	private CalculateInterface calc = null;
    public UiBindingContainer uiBindingContainer = null;

	public CalcWatcher(CalculateInterface calculateInterface, UiBindingContainer editor) {
		calc = calculateInterface;
        uiBindingContainer = editor;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before,
			int count) {
        uiBindingContainer.suspendChangeNotification();
        calc.Calculate();
        uiBindingContainer.resumeChangeNotification();
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}
}