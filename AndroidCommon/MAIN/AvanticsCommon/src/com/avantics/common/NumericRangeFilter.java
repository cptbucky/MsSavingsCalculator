package com.avantics.common;

import android.text.InputFilter;
import android.text.Spanned;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Numeric range Filter.
 */
class NumericRangeFilter implements InputFilter {
    Pattern mPattern;

    private NumberFormat format = null;

    /**
     * Creates a new filter between 0.00 and 999,999.99.
     *
     * @param formatter
     */
    NumericRangeFilter(NumberFormat formatter) {
        format = formatter;

        mPattern = Pattern.compile("^((\\d+||\\d+\\.||\\.)\\d{0,2})$");
    }

    @Override
    public CharSequence filter(CharSequence p_source, int p_start, int p_end,
                               Spanned p_dest, int p_dstart, int p_dend) {

        String stringToBeReplaced = p_dest.subSequence(p_dstart, p_dend)
                .toString();

        String newEntry = "";

        try {
            newEntry = p_dest.toString();
            if (!stringToBeReplaced.equals(""))
                newEntry = newEntry.replace(stringToBeReplaced, "");
            newEntry = newEntry.concat(p_source.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        double newValue = 0.00;

        // get rid of extra formatting
        try {
            newValue = format.parse(newEntry).doubleValue();
        } catch (ParseException e) {
            try {
                newValue = Double.parseDouble(newEntry);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        Matcher matcher = mPattern.matcher(String.valueOf(newValue));
        if (!matcher.matches()) {
            return "";
        }

        return null;
    }
}