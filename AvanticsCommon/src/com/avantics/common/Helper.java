package com.avantics.common;

import java.text.NumberFormat;
import java.text.ParseException;

import android.widget.TextView;

public class Helper {

	public static double getDoubleFrom(TextView view) {
		double val = getDoubleFrom(view.getText().toString());

		return val;
	}

	public static double getDoubleFrom(String txt) {
		double val = 0;

		if (!"".equals(txt.replace(" ", "")) && !".".equals(txt)
				&& !",".equals(txt) && !"-".equals(txt)) {
			val = Double.parseDouble(txt);
		}

		return val;
	}

	public static double getDoubleFrom(CharSequence chars) {
		double val = 0;

		if (!"".equals(chars.toString())) {
			val = Double.parseDouble(chars.toString());
		}

		return val;
	}

	public static double getDoubleFromCurrencyString(String formattedString) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();

		// really lazy this shouldn't blindly check for whether it is a currency or not..
		try {
			return nf.parse(formattedString).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return getDoubleFrom(formattedString);
	}

	public static void calculateT(double ccst, double ccfr, TextView target) {
		double val = (ccst * ccfr);
		String stringVal = Helper.getStringFrom(val);
		target.setText(stringVal);
	}

	public static String getStringFrom(double total) {
		return String.format("%.2f", total);
	}

	public static String getCurrencyStringFrom(double total) {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(total);
	}
}