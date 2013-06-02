package com.avantics.savingscalc.common;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avantics.common.Formatters;
import com.avantics.common.UiBindingContainer;

public class UiBindingManager {
    public Formatters formatters = null;

    public String name = "";

    public UiBindingContainer cstet = null;
    public UiBindingContainer csterminal = null;
    public UiBindingContainer cstotal = null;

    public UiBindingContainer ccst = null;
    public UiBindingContainer ccfr = null;
    public UiBindingContainer cct = null;

    public UiBindingContainer bcst = null;
    public UiBindingContainer bcfr = null;
    public UiBindingContainer bct = null;

    public UiBindingContainer dcst = null;
    public UiBindingContainer dcfr = null;
    public UiBindingContainer dct = null;

    public UiBindingContainer fincpci = null;
    public UiBindingContainer fincpcirate;

    public UiBindingContainer stet = null;

    public UiBindingContainer vendorterminal = null;
    public UiBindingContainer vendorterminaltotal = null;

    public UiBindingContainer savingsPercentage = null;
    public UiBindingContainer savings1month = null;
    public UiBindingContainer savings1year = null;
    public UiBindingContainer savings4years = null;

    public UiBindingManager(View view) {
        formatters = new Formatters();

        cstet = new UiBindingContainer(
                formatters.getCurrencyEditor((EditText) view.findViewById(R.id.CSTotalExcTerminal)),
                formatters.CURRENCY_FORMATTER);
        csterminal = new UiBindingContainer(
                formatters.getCurrencyEditor((EditText) view.findViewById(R.id.CSTerminal)),
                formatters.CURRENCY_FORMATTER);
        cstotal = new UiBindingContainer(
                (TextView) view.findViewById(R.id.CSTotal),
                formatters.CURRENCY_FORMATTER);

        ccst = new UiBindingContainer(formatters.getCurrencyEditor((EditText) view.findViewById(R.id.CCST)),
                formatters.CURRENCY_FORMATTER);
        ccfr = new UiBindingContainer(formatters.getDecimalEditor((EditText) view.findViewById(R.id.CCFR)),
                formatters.DECIMAL_FORMATTER);
        cct = new UiBindingContainer(
                (TextView) view.findViewById(R.id.CCT),
                formatters.CURRENCY_FORMATTER);

        bcst = new UiBindingContainer(formatters.getCurrencyEditor((EditText) view.findViewById(R.id.BCST)),
                formatters.CURRENCY_FORMATTER);
        bcfr = new UiBindingContainer(formatters.getDecimalEditor((EditText) view.findViewById(R.id.BCFR)),
                formatters.DECIMAL_FORMATTER);
        bct = new UiBindingContainer(
                (TextView) view.findViewById(R.id.BCT),
                formatters.CURRENCY_FORMATTER);

        dcst = new UiBindingContainer(formatters.getDecimalEditor((EditText) view.findViewById(R.id.DCST)),
                formatters.DECIMAL_FORMATTER);
        dcfr = new UiBindingContainer(formatters.getCurrencyEditor((EditText) view.findViewById(R.id.DCFR)),
                formatters.CURRENCY_FORMATTER);
        dct = new UiBindingContainer(
                (TextView) view.findViewById(R.id.DCT),
                formatters.CURRENCY_FORMATTER);

        fincpci = new UiBindingContainer((TextView) view.findViewById(R.id.FIncPci),
                formatters.CURRENCY_FORMATTER);
        fincpcirate = new UiBindingContainer(formatters.getDecimalEditor((EditText) view.findViewById(R.id.FIncPciRate)),
                formatters.DECIMAL_FORMATTER);

        stet = new UiBindingContainer(
                formatters.getCurrencyEditor((EditText) view.findViewById(R.id.CSTotalExcTerminal)),
                formatters.CURRENCY_FORMATTER);

        vendorterminal = new UiBindingContainer(
                formatters.getCurrencyEditor((EditText) view.findViewById(R.id.VendorTerminal)),
                formatters.CURRENCY_FORMATTER);
        vendorterminaltotal = new UiBindingContainer(
                (TextView) view.findViewById(R.id.FpmsTerminalTotal),
                formatters.CURRENCY_FORMATTER);

        savingsPercentage = new UiBindingContainer(
                (TextView) view.findViewById(R.id.savingsPercentage),
                formatters.PERCENTAGE_FORMATTER);
        savings1month = new UiBindingContainer(
                (TextView) view.findViewById(R.id.Savings1Month),
                formatters.CURRENCY_FORMATTER);
        savings1year = new UiBindingContainer(
                (TextView) view.findViewById(R.id.Savings1Year),
                formatters.CURRENCY_FORMATTER);
        savings4years = new UiBindingContainer(
                (TextView) view.findViewById(R.id.Savings4Years),
                formatters.CURRENCY_FORMATTER);
    }

    public Quote getSelectedQuote() {
        Quote quote = new Quote();
        quote.Name = name;

        quote.cstet = cstet.getValue();
        quote.csterminal = csterminal.getValue();

        quote.ccst = ccst.getValue();
        quote.ccfr = ccfr.getValue();

        quote.bcst = bcst.getValue();
        quote.bcfr = bcfr.getValue();

        quote.dcst = dcst.getValue();
        quote.dcfr = dcfr.getValue();

        quote.fincpcirate = fincpcirate.getValue();

        quote.vendorterminal = vendorterminal.getValue();

        return quote;
    }

    public void setSelectedQuote(Quote quote) {
        name = quote.Name;

        cstet.setValue(quote.cstet);
        csterminal.setValue(quote.csterminal);

        ccst.setValue(quote.ccst);
        ccfr.setValue(quote.ccfr);

        bcst.setValue(quote.bcst);
        bcfr.setValue(quote.bcfr);

        dcst.setValue(quote.dcst);
        dcfr.setValue(quote.dcfr);

        fincpcirate.setValue(quote.fincpcirate);

        vendorterminal.setValue(quote.vendorterminal);
    }

    public void resetQuote() {
        name = "";

        cstet.defaultToFormattedZero();
        csterminal.defaultToFormattedZero();

        ccst.defaultToFormattedZero();
        ccfr.defaultToFormattedZero();

        bcst.defaultToFormattedZero();
        bcfr.defaultToFormattedZero();

        dcst.defaultToFormattedZero();
        dcfr.defaultToFormattedZero();

        fincpcirate.defaultToFormattedZero();

        vendorterminal.defaultToFormattedZero();
    }
}
