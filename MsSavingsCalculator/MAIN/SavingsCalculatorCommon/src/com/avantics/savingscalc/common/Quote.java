package com.avantics.savingscalc.common;

import com.avantics.common.BindableProperty;
import com.avantics.common.OnValueChangedEventListener;

public class Quote {
    public int Id = -1;
    public BindableProperty<String> Name;

    public BindableProperty<Double> CustomerTerminal;
    public BindableProperty<Double> CustomerTotalExcludingTerminal;
    public BindableProperty<Double> CustomerStatementTotal;

    public BindableProperty<Double> CreditCardStatementTotal;
    public BindableProperty<Double> CreditCardRate;
    public BindableProperty<Double> CreditCardTotal;

    public BindableProperty<Double> BankCardStatementTotal;
    public BindableProperty<Double> BankCardRate;
    public BindableProperty<Double> BankCardTotal;

    public BindableProperty<Double> DebitCardStatementTotal;
    public BindableProperty<Double> DebitCardRate;
    public BindableProperty<Double> DebitCardTotal;

    public BindableProperty<Double> FIncludingPciRate;
    public BindableProperty<Double> FIncludingPciTotal;

    public BindableProperty<Double> VendorTerminal;
    public BindableProperty<Double> VendorTerminalTotal;

    public BindableProperty<Double> SavingsPercentage;
    public BindableProperty<Double> SavingsOneMonth;
    public BindableProperty<Double> SavingsOneYear;
    public BindableProperty<Double> SavingsFourYears;

    public Quote() {

        Name = new BindableProperty<String>("");

        OnValueChangedEventListener customerStatementTotals = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                CustomerStatementTotal.setValue(CustomerTerminal.getValue() + CustomerTotalExcludingTerminal.getValue());
            }
        };

        CustomerTotalExcludingTerminal = new BindableProperty<Double>(0.00, customerStatementTotals);
        CustomerTerminal = new BindableProperty<Double>(0.00, customerStatementTotals);

        CustomerStatementTotal = new BindableProperty<Double>(0.00);

        OnValueChangedEventListener fIncPciTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                double total = CreditCardTotal.getValue() + BankCardTotal.getValue() + DebitCardTotal.getValue() + FIncludingPciRate.getValue();

                FIncludingPciTotal.setValue(total);
            }
        };

        OnValueChangedEventListener creditCardTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                double total = CreditCardStatementTotal.getValue() * (CreditCardRate.getValue() / 100);

                CreditCardTotal.setValue(total);
            }
        };

        CreditCardStatementTotal = new BindableProperty<Double>(0.00, creditCardTotal);
        CreditCardRate = new BindableProperty<Double>(0.00, creditCardTotal);

        CreditCardTotal = new BindableProperty<Double>(0.00, fIncPciTotal);

        OnValueChangedEventListener bankCardTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                double total = BankCardStatementTotal.getValue() * (BankCardRate.getValue() / 100);

                BankCardTotal.setValue(total);
            }
        };

        BankCardStatementTotal = new BindableProperty<Double>(0.00, bankCardTotal);
        BankCardRate = new BindableProperty<Double>(0.00, bankCardTotal);

        BankCardTotal = new BindableProperty<Double>(0.00, fIncPciTotal);

        OnValueChangedEventListener debitCardTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                double total = DebitCardStatementTotal.getValue() * DebitCardRate.getValue();

                DebitCardTotal.setValue(total);
            }
        };

        DebitCardStatementTotal = new BindableProperty<Double>(0.00, debitCardTotal);
        DebitCardRate = new BindableProperty<Double>(0.00, debitCardTotal);

        DebitCardTotal = new BindableProperty<Double>(0.00, fIncPciTotal);

        FIncludingPciRate = new BindableProperty<Double>(0.00, fIncPciTotal);

        OnValueChangedEventListener vTerminalTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                double total = FIncludingPciTotal.getValue() + VendorTerminal.getValue();

                VendorTerminalTotal.setValue(total);
            }
        };

        FIncludingPciTotal = new BindableProperty<Double>(0.00, vTerminalTotal);

        VendorTerminal = new BindableProperty<Double>(0.00, vTerminalTotal);

        OnValueChangedEventListener savingsPercentage = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                double total = (CustomerStatementTotal.getValue() / VendorTerminalTotal.getValue()) * 100;

                SavingsPercentage.setValue(total);

                double actualSaving = CustomerStatementTotal.getValue() - VendorTerminalTotal.getValue();

                SavingsOneMonth.setValue(actualSaving);
                SavingsOneYear.setValue(actualSaving * 12);
                SavingsFourYears.setValue(actualSaving * 48);
            }
        };

        VendorTerminalTotal = new BindableProperty<Double>(0.00);

        SavingsPercentage = new BindableProperty<Double>(0.00);
        SavingsOneMonth = new BindableProperty<Double>(0.00);
        SavingsOneYear = new BindableProperty<Double>(0.00);
        SavingsFourYears = new BindableProperty<Double>(0.00);
    }
}
