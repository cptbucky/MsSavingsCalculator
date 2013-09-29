package com.avantics.savingscalc.common.entities;

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

    public BindableProperty<Double> BusinessCardStatementTotal;
    public BindableProperty<Double> BusinessCardRate;
    public BindableProperty<Double> BusinessCardTotal;

    public BindableProperty<Double> DebitCardStatementTotal;
    public BindableProperty<Double> DebitCardRate;
    public BindableProperty<Double> DebitCardTotal;

    public BindableProperty<Double> FIncludingPciRate;
    public BindableProperty<Double> PciDssTotal;

    public BindableProperty<Double> VendorTerminal;
    public BindableProperty<Double> VendorTerminalTotal;

    public BindableProperty<Double> SavingsPercentage;
    public BindableProperty<Double> SavingsOneMonth;
    public BindableProperty<Double> SavingsOneYear;
    public BindableProperty<Double> SavingsFourYears;

    private boolean changed = false;

    public Quote() {

        Name = new BindableProperty<String>("");

        OnValueChangedEventListener customerStatementTotals = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                changed = true;

                CustomerStatementTotal.setValue(CustomerTerminal.getValue() + CustomerTotalExcludingTerminal.getValue());
            }
        };

        CustomerTotalExcludingTerminal = new BindableProperty<Double>(0.00, customerStatementTotals);
        CustomerTerminal = new BindableProperty<Double>(0.00, customerStatementTotals);

        OnValueChangedEventListener savingsPercentage = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                changed = true;

                double actualSaving = CustomerStatementTotal.getValue() - VendorTerminalTotal.getValue();

                SavingsOneMonth.setValue(actualSaving);
                SavingsOneYear.setValue(actualSaving * 12);
                SavingsFourYears.setValue(actualSaving * 48);

                double total = (actualSaving / CustomerStatementTotal.getValue());

                if (Double.isInfinite(total) || Double.isNaN(total)) {
                    SavingsPercentage.setValue(0.00);
                } else {
                    SavingsPercentage.setValue(total);
                }
            }
        };

        CustomerStatementTotal = new BindableProperty<Double>(0.00, savingsPercentage);

        OnValueChangedEventListener pciDssTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                changed = true;

                double total = CreditCardTotal.getValue() + BusinessCardTotal.getValue() + DebitCardTotal.getValue() + FIncludingPciRate.getValue();

                PciDssTotal.setValue(total);
            }
        };

        OnValueChangedEventListener creditCardTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                changed = true;

                double total = CreditCardStatementTotal.getValue() * (CreditCardRate.getValue() / 100);

                CreditCardTotal.setValue(total);
            }
        };

        CreditCardStatementTotal = new BindableProperty<Double>(0.00, creditCardTotal);
        CreditCardRate = new BindableProperty<Double>(0.00, creditCardTotal);

        CreditCardTotal = new BindableProperty<Double>(0.00, pciDssTotal);

        OnValueChangedEventListener businessCardTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                changed = true;

                double total = BusinessCardStatementTotal.getValue() * (BusinessCardRate.getValue() / 100);

                BusinessCardTotal.setValue(total);
            }
        };

        BusinessCardStatementTotal = new BindableProperty<Double>(0.00, businessCardTotal);
        BusinessCardRate = new BindableProperty<Double>(0.00, businessCardTotal);

        BusinessCardTotal = new BindableProperty<Double>(0.00, pciDssTotal);

        OnValueChangedEventListener debitCardTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                changed = true;

                double total = DebitCardStatementTotal.getValue() * DebitCardRate.getValue();

                DebitCardTotal.setValue(total);
            }
        };

        DebitCardStatementTotal = new BindableProperty<Double>(0.00, debitCardTotal);
        DebitCardRate = new BindableProperty<Double>(0.00, debitCardTotal);

        DebitCardTotal = new BindableProperty<Double>(0.00, pciDssTotal);

        FIncludingPciRate = new BindableProperty<Double>(0.00, pciDssTotal);

        OnValueChangedEventListener vTerminalTotal = new OnValueChangedEventListener() {
            @Override
            public void OnValueChanged() {
                changed = true;

                double total = PciDssTotal.getValue() + VendorTerminal.getValue();

                VendorTerminalTotal.setValue(total);
            }
        };

        PciDssTotal = new BindableProperty<Double>(0.00, vTerminalTotal);

        VendorTerminal = new BindableProperty<Double>(0.00, vTerminalTotal);
        VendorTerminalTotal = new BindableProperty<Double>(0.00, savingsPercentage);

        SavingsPercentage = new BindableProperty<Double>(0.00);
        SavingsOneMonth = new BindableProperty<Double>(0.00);
        SavingsOneYear = new BindableProperty<Double>(0.00);
        SavingsFourYears = new BindableProperty<Double>(0.00);
    }

    public void ResetChangesFlag() {
        changed = false;
    }

    public boolean HasChanged() {
        return changed;
    }
}
