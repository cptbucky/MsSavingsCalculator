package com.avantics.common;

import android.view.View;
import android.widget.EditText;

import java.text.NumberFormat;
import java.text.ParseException;

class AmountOnFocusChangeListener implements View.OnFocusChangeListener {
    private NumberFormat format = null;

    AmountOnFocusChangeListener(NumberFormat formatter) {
        format = formatter;
    }

    @Override
    public void onFocusChange(View p_view, boolean p_hasFocus) {
        EditText v_amountView = (EditText) p_view;

        if (p_hasFocus) {
            String valString = v_amountView.getText().toString();

            double value = 0;
            try {
                value = format.parse(valString).doubleValue();
            } catch (ParseException e) {
                try {
                    value = Double.parseDouble(valString);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }

            v_amountView.setText(String.valueOf(value));

            // Select all so the user can overwrite the entire amount in one shot.
            v_amountView.selectAll();
        } else {
            // base string
            String v_string = v_amountView.getText().toString();

            // get double from string, default to zero when empty
            double v_double = Helper.getDoubleFrom(v_string);

            // format to currency
            v_string = format.format(v_double);

            v_amountView.setText(v_string);
        }
    }
}