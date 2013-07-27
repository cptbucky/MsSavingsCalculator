package com.avantics.savingscalc.common;

import com.avantics.common.BindableProperty;

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

    public Quote(){

        Name = new BindableProperty<String>("");

        CustomerTotalExcludingTerminal = new BindableProperty<Double>(0.00);
        CustomerTerminal = new BindableProperty<Double>(0.00);
        CustomerStatementTotal = new BindableProperty<Double>(0.00);

        CreditCardStatementTotal = new BindableProperty<Double>(0.00);
        CreditCardRate = new BindableProperty<Double>(0.00);
        CreditCardTotal = new BindableProperty<Double>(0.00);

        BankCardStatementTotal = new BindableProperty<Double>(0.00);
        BankCardRate = new BindableProperty<Double>(0.00);
        BankCardTotal = new BindableProperty<Double>(0.00);

        DebitCardStatementTotal = new BindableProperty<Double>(0.00);
        DebitCardRate = new BindableProperty<Double>(0.00);
        DebitCardTotal = new BindableProperty<Double>(0.00);

        FIncludingPciRate = new BindableProperty<Double>(0.00);
        FIncludingPciTotal = new BindableProperty<Double>(0.00);

        VendorTerminal = new BindableProperty<Double>(0.00);
        VendorTerminalTotal = new BindableProperty<Double>(0.00);
    }
}
