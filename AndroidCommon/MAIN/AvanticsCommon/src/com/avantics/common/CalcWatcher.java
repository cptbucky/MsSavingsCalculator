package com.avantics.common;

import android.text.Editable;
import android.text.TextWatcher;

public class CalcWatcher implements TextWatcher {
	private CalculateInterface calc = null;

	public CalcWatcher(CalculateInterface calculateInterface) {
		calc = calculateInterface;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before,
			int count) {
		calc.Calculate();
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}
}