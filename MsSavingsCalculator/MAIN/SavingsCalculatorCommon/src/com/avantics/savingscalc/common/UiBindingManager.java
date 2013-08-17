package com.avantics.savingscalc.common;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.avantics.common.*;

import java.text.NumberFormat;
import java.util.ArrayList;

public class UiBindingManager implements IBindManager {
    public Formatters formatters = null;

    public Quote currentQuote;

    public String name = "";

//    public UiBindingContainer cstet = null;
//    public UiBindingContainer csterminal = null;
//    public UiBindingContainer cstotal = null;
//
//    public UiBindingContainer ccst = null;
//    public UiBindingContainer ccfr = null;
//    public UiBindingContainer cct = null;
//
//    public UiBindingContainer bcst = null;
//    public UiBindingContainer bcfr = null;
//    public UiBindingContainer bct = null;
//
//    public UiBindingContainer dcst = null;
//    public UiBindingContainer dcfr = null;
//    public UiBindingContainer dct = null;
//
//    public UiBindingContainer fincpci = null;
//    public UiBindingContainer fincpcirate;
//
//    public UiBindingContainer vendorterminal = null;
//    public UiBindingContainer vendorTerminalTotal = null;

//    public UiBindingContainer savingsPercentage = null;
//    public UiBindingContainer savings1month = null;
//    public UiBindingContainer savings1year = null;
//    public UiBindingContainer savings4years = null;

    ArrayList<UiBindingContainer> containedControls = new ArrayList<UiBindingContainer>();

    public UiBindingManager() {
        formatters = new Formatters();

        currentQuote = new Quote();
    }

    @Override
    public ArrayList<UiBindingContainer> AttachToView(View view) {
        containedControls = new ArrayList<UiBindingContainer>();

//        CalculateInterface oneMonthCalculator = new CalculateInterface() {
//            @Override
//            public void Calculate() {
//                double total = currentQuote.CustomerStatementTotal.getValue() - currentQuote.CustomerTerminal.getValue();
//
//                savings1month.setValue(total);
//
//                double percentageIncrease = (total / currentQuote.CustomerStatementTotal.getValue()) * 100;
//
//                double value = Double.isNaN(percentageIncrease)
//                        || Double.isInfinite(percentageIncrease) ? 0.00
//                        : percentageIncrease;
//
//                savingsPercentage.setValue(value);
//
//                double total1year = savings1month.getValue() * 12; // 1 year
//
//                savings1year.setValue(total1year);
//
//                double total4years = savings1month.getValue() * 48; // 2 years
//
//                savings4years.setValue(total4years);
//            }
//        };

//        CalculateInterface csTotalCalculator = new CalculateInterface() {
//            @Override
//            public void Calculate() {
////                if (cstotal == null) // | csterminal == null | cstet == null
////                    return;
//
//                currentQuote.CustomerStatementTotal.setValue(currentQuote.CustomerTerminal.getValue() + currentQuote.CustomerTotalExcludingTerminal.getValue());
//            }
//        };

        getUiBindingContainer((EditText) view.findViewById(R.id.CSTotalExcTerminal), currentQuote.CustomerTotalExcludingTerminal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.CSTerminal), currentQuote.CustomerTerminal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.CSTotal), currentQuote.CustomerStatementTotal, formatters.CURRENCY_FORMATTER);

        CalculateInterface fIncCalculator = new CalculateInterface() {
            @Override
            public void Calculate() {
                double total = currentQuote.CreditCardTotal.getValue() + currentQuote.BankCardTotal.getValue()
                        + currentQuote.DebitCardTotal.getValue() + currentQuote.FIncludingPciRate.getValue();

                currentQuote.FIncludingPciTotal.setValue(total);
            }
        };

//        CalculateInterface cctCalculator = new CalculateInterface() {
//            @Override
//            public void Calculate() {
//                double total = currentQuote.CreditCardStatementTotal.getValue() * (currentQuote.CreditCardRate.getValue() / 100);
//
//                currentQuote.CreditCardTotal.setValue(total);
//            }
//        };

        getUiBindingContainer((EditText) view.findViewById(R.id.CCST), currentQuote.CreditCardStatementTotal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.CCFR), currentQuote.CreditCardRate, formatters.DECIMAL_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.CCT), currentQuote.CreditCardTotal, formatters.CURRENCY_FORMATTER);

//        CalculateInterface bctCalculator = new CalculateInterface() {
//            @Override
//            public void Calculate() {
//                double total = currentQuote.BankCardStatementTotal.getValue() * (currentQuote.BankCardRate.getValue() / 100);
//
//                currentQuote.BankCardTotal.setValue(total);
//            }
//        };

        getUiBindingContainer((EditText) view.findViewById(R.id.BCST), currentQuote.BankCardStatementTotal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.BCFR), currentQuote.BankCardRate, formatters.DECIMAL_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.BCT), currentQuote.BankCardTotal, formatters.CURRENCY_FORMATTER);

//        CalculateInterface dctCalculator = new CalculateInterface() {
//            @Override
//            public void Calculate() {
//                double total = currentQuote.DebitCardStatementTotal.getValue() * currentQuote.DebitCardRate.getValue();
//
//                currentQuote.DebitCardTotal.setValue(total);
//            }
//        };

        getUiBindingContainer((EditText) view.findViewById(R.id.DCST), currentQuote.DebitCardStatementTotal, formatters.DECIMAL_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.DCFR), currentQuote.DebitCardRate, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.DCT), currentQuote.DebitCardTotal, formatters.CURRENCY_FORMATTER);

        CalculateInterface vendorTerminalTotalCalculator = new CalculateInterface() {
            @Override
            public void Calculate() {
                double total = currentQuote.FIncludingPciTotal.getValue() + currentQuote.VendorTerminal.getValue();

                currentQuote.VendorTerminalTotal.setValue(total);
            }
        };

        getUiBindingContainer((TextView) view.findViewById(R.id.FIncPci), currentQuote.FIncludingPciTotal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.FIncPciRate), currentQuote.FIncludingPciRate, formatters.CURRENCY_FORMATTER);

        getUiBindingContainer((EditText) view.findViewById(R.id.VendorTerminal), currentQuote.VendorTerminal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.VendorTerminalTotal), currentQuote.VendorTerminalTotal, formatters.CURRENCY_FORMATTER);

        getUiBindingContainer((TextView) view.findViewById(R.id.savingsPercentage), currentQuote.SavingsPercentage, formatters.PERCENTAGE_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.Savings1Month), currentQuote.SavingsOneMonth, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.Savings1Year), currentQuote.SavingsOneYear, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.Savings4Years), currentQuote.SavingsFourYears, formatters.CURRENCY_FORMATTER);

        return containedControls;
    }

//    private UiBindingContainer getUiBindingContainer(EditText editor, UiBindingContainer container, NumberFormat formatter, CalculateInterface callback) {
//        if (editor == null){
//            return container;
//        }
//
//        EditText wrappedEditor = null;
//
//        if (formatter == formatters.CURRENCY_FORMATTER){
//            wrappedEditor = formatters.getCurrencyEditor(editor);
//        } else if (formatter == formatters.DECIMAL_FORMATTER){
//            wrappedEditor = formatters.getDecimalEditor(editor);
//        } else {
//            throw new IllegalArgumentException("formatter not recognised.");
//        }
//
//        return new UiBindingContainer(wrappedEditor, formatter, callback, container);
//    }

    private void getUiBindingContainer(EditText editor, BindableProperty property, NumberFormat formatter) {
        if (editor == null) {
            return;
        }

        EditText wrappedEditor = null;

        if (formatter == formatters.CURRENCY_FORMATTER) {
            wrappedEditor = formatters.getCurrencyEditor(editor);
        } else if (formatter == formatters.DECIMAL_FORMATTER) {
            wrappedEditor = formatters.getDecimalEditor(editor);
        } else {
            throw new IllegalArgumentException("formatter not recognised.");
        }

        UiBindingContainer contained = new UiBindingContainer(wrappedEditor, formatter, property);

        // 2 lists looks wrong... must be a better way to structure this.
        containedControls.add(contained);

        property.addListener(contained);
    }

//    private void getUiBindingContainer(TextView editor, BindableProperty container, NumberFormat formatter) {
//        getUiBindingContainer(editor, container, formatter, null);
//    }

    private void getUiBindingContainer(TextView editor, BindableProperty property, NumberFormat formatter) {
        if (editor == null) {
            return;
        }

        UiBindingContainer newWrapper = new UiBindingContainer(editor, formatter, property);

        property.addListener(newWrapper);
    }

//    public Quote getSelectedQuote() {
//        Quote quote = new Quote();
//        quote.Name.setValue(name);
//
//        quote.CustomerTotalExcludingTerminal.setValue(cstet.getValue());
//        quote.CustomerTerminal.setValue(csterminal.getValue());
//
//        quote.CreditCardStatementTotal.setValue(ccst.getValue());
//        quote.CreditCardRate.setValue(ccfr.getValue());
//
//        quote.BankCardStatementTotal.setValue(bcst.getValue());
//        quote.BankCardRate.setValue(bcfr.getValue());
//
//        quote.DebitCardStatementTotal.setValue(dcst.getValue());
//        quote.DebitCardRate.setValue(dcfr.getValue());
//
//        quote.FIncludingPciRate.setValue(fincpcirate.getValue());
//
//        quote.VendorTerminal.setValue(vendorterminal.getValue());
//
//        return quote;
//    }
//
//    public void setSelectedQuote(Quote quote) {
//        name = quote.Name.getValue();
//
//        cstet.setValue(quote.CustomerTotalExcludingTerminal.getValue());
//        csterminal.setValue(quote.CustomerTerminal.getValue());
//
//        ccst.setValue(quote.CreditCardStatementTotal.getValue());
//        ccfr.setValue(quote.CreditCardRate.getValue());
//
//        bcst.setValue(quote.BankCardStatementTotal.getValue());
//        bcfr.setValue(quote.BankCardRate.getValue());
//
//        dcst.setValue(quote.DebitCardStatementTotal.getValue());
//        dcfr.setValue(quote.DebitCardRate.getValue());
//
//        fincpcirate.setValue(quote.FIncludingPciRate.getValue());
//
//        vendorterminal.setValue(quote.VendorTerminal.getValue());
//    }
//
//    public void resetQuote() {
//        name = "";
//
//        cstet.defaultToFormattedZero();
//        csterminal.defaultToFormattedZero();
//
//        ccst.defaultToFormattedZero();
//        ccfr.defaultToFormattedZero();
//
//        bcst.defaultToFormattedZero();
//        bcfr.defaultToFormattedZero();
//
//        dcst.defaultToFormattedZero();
//        dcfr.defaultToFormattedZero();
//
//        fincpcirate.defaultToFormattedZero();
//
//        vendorterminal.defaultToFormattedZero();
//    }
}
