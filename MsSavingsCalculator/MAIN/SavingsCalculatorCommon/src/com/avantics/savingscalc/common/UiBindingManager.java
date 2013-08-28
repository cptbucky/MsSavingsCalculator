package com.avantics.savingscalc.common;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.avantics.common.BindableProperty;
import com.avantics.common.Formatters;
import com.avantics.common.IBindManager;
import com.avantics.common.UiBindingContainer;

import java.text.NumberFormat;
import java.util.ArrayList;

public class UiBindingManager implements IBindManager {
    public Formatters formatters = null;

    public Quote currentQuote;

    ArrayList<UiBindingContainer> containedControls = new ArrayList<UiBindingContainer>();

    public UiBindingManager() {
        formatters = new Formatters();

        currentQuote = new Quote();
    }

    @Override
    public ArrayList<UiBindingContainer> AttachToView(View view) {
        containedControls = new ArrayList<UiBindingContainer>();

        getUiBindingContainer((EditText) view.findViewById(R.id.CSTotalExcTerminal), currentQuote.CustomerTotalExcludingTerminal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.CSTerminal), currentQuote.CustomerTerminal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.CSTotal), currentQuote.CustomerStatementTotal, formatters.CURRENCY_FORMATTER);

        getUiBindingContainer((EditText) view.findViewById(R.id.CCST), currentQuote.CreditCardStatementTotal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.CCFR), currentQuote.CreditCardRate, formatters.DECIMAL_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.CCT), currentQuote.CreditCardTotal, formatters.CURRENCY_FORMATTER);

        getUiBindingContainer((EditText) view.findViewById(R.id.BCST), currentQuote.BankCardStatementTotal, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.BCFR), currentQuote.BankCardRate, formatters.DECIMAL_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.BCT), currentQuote.BankCardTotal, formatters.CURRENCY_FORMATTER);

        getUiBindingContainer((EditText) view.findViewById(R.id.DCST), currentQuote.DebitCardStatementTotal, formatters.DECIMAL_FORMATTER);
        getUiBindingContainer((EditText) view.findViewById(R.id.DCFR), currentQuote.DebitCardRate, formatters.CURRENCY_FORMATTER);
        getUiBindingContainer((TextView) view.findViewById(R.id.DCT), currentQuote.DebitCardTotal, formatters.CURRENCY_FORMATTER);

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

    private void getUiBindingContainer(TextView editor, BindableProperty property, NumberFormat formatter) {
        if (editor == null) {
            return;
        }

        editor.setText(formatter.format(property.getValue()));

        UiBindingContainer newWrapper = new UiBindingContainer(editor, formatter, property);

        property.addListener(newWrapper);
    }

    public Quote getSelectedQuote() {
        return currentQuote;
    }

    public void setSelectedQuote(Quote quote) {
        currentQuote.Name.setValue(quote.Name.getValue());

        currentQuote.CustomerTotalExcludingTerminal.setValue(quote.CustomerTotalExcludingTerminal.getValue());
        currentQuote.CustomerTerminal.setValue(quote.CustomerTerminal.getValue());

        currentQuote.CreditCardStatementTotal.setValue(quote.CreditCardStatementTotal.getValue());
        currentQuote.CreditCardRate.setValue(quote.CreditCardRate.getValue());

        currentQuote.BankCardStatementTotal.setValue(quote.BankCardStatementTotal.getValue());
        currentQuote.BankCardRate.setValue(quote.BankCardRate.getValue());

        currentQuote.DebitCardStatementTotal.setValue(quote.DebitCardStatementTotal.getValue());
        currentQuote.DebitCardRate.setValue(quote.DebitCardRate.getValue());

        currentQuote.FIncludingPciRate.setValue(quote.FIncludingPciRate.getValue());

        currentQuote.VendorTerminal.setValue(quote.VendorTerminal.getValue());

        currentQuote.ResetChangesFlag();
    }

    public void resetQuote() {
        currentQuote.Name.setValue("");

        currentQuote.CustomerTotalExcludingTerminal.setValue(0.00);
        currentQuote.CustomerTerminal.setValue(0.00);

        currentQuote.CreditCardStatementTotal.setValue(0.00);
        currentQuote.CreditCardRate.setValue(0.00);

        currentQuote.BankCardStatementTotal.setValue(0.00);
        currentQuote.BankCardRate.setValue(0.00);

        currentQuote.DebitCardStatementTotal.setValue(0.00);
        currentQuote.DebitCardRate.setValue(0.00);

        currentQuote.FIncludingPciRate.setValue(0.00);

        currentQuote.VendorTerminal.setValue(0.00);
    }
}
