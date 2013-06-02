package com.avantics.savingscalc.common.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avantics.common.CalcWatcher;
import com.avantics.common.CalculateInterface;
import com.avantics.savingscalc.common.R;
import com.avantics.savingscalc.common.UiBindingManager;

public class QuoteFragment extends Fragment {

    public UiBindingManager binder = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quote_form, container, false);

        binder = new UiBindingManager(view);

        TextWatcher fincpciWatcher = new CalcWatcher(new CalculateInterface() {
            @Override
            public void Calculate() {
                double total = binder.cct.getValue() + binder.bct.getValue()
                        + binder.dct.getValue() + binder.fincpcirate.getValue();

                binder.fincpci.setValue(total);
            }
        });

        TextWatcher cctWatcher = new CalcWatcher(new CalculateInterface() {
            @Override
            public void Calculate() {
                double total = binder.ccst.getValue()
                        * (binder.ccfr.getValue() / 100);

                binder.cct.setValue(total);
            }
        });

        binder.ccfr.Ctrl.addTextChangedListener(cctWatcher);

        binder.ccst.Ctrl.addTextChangedListener(cctWatcher);

        binder.cct.Ctrl.addTextChangedListener(fincpciWatcher);

        TextWatcher bctWatcher = new CalcWatcher(new CalculateInterface() {
            @Override
            public void Calculate() {
                double total = binder.bcst.getValue()
                        * (binder.bcfr.getValue() / 100);

                binder.bct.setValue(total);
            }
        });

        binder.bcfr.Ctrl.addTextChangedListener(bctWatcher);

        binder.bcst.Ctrl.addTextChangedListener(bctWatcher);

        binder.bct.Ctrl.addTextChangedListener(fincpciWatcher);

        TextWatcher dctWatcher = new CalcWatcher(new CalculateInterface() {
            @Override
            public void Calculate() {
                double total = binder.dcst.getValue() * binder.dcfr.getValue();

                binder.dct.setValue(total);
            }
        });

        binder.dcfr.Ctrl.addTextChangedListener(dctWatcher);

        binder.dcst.Ctrl.addTextChangedListener(dctWatcher);

        binder.dct.Ctrl.addTextChangedListener(fincpciWatcher);

        binder.fincpcirate.Ctrl.addTextChangedListener(fincpciWatcher);

        TextWatcher fpmsTerminalTotalWatcher = new CalcWatcher(
                new CalculateInterface() {
                    @Override
                    public void Calculate() {
                        double total = binder.fincpci.getValue()
                                + binder.vendorterminal.getValue();

                        binder.vendorterminaltotal.setValue(total);
                    }
                });

        binder.vendorterminal.Ctrl.addTextChangedListener(fpmsTerminalTotalWatcher);

        binder.fincpci.Ctrl.addTextChangedListener(fpmsTerminalTotalWatcher);

        TextWatcher csTotalTotalWatcher = new CalcWatcher(
                new CalculateInterface() {
                    @Override
                    public void Calculate() {
                        binder.cstotal.setValue(binder.csterminal.getValue()
                                + binder.cstet.getValue());
                    }
                });

        binder.cstet.Ctrl.addTextChangedListener(csTotalTotalWatcher);

        binder.csterminal.Ctrl.addTextChangedListener(csTotalTotalWatcher);

        TextWatcher oneMonthTotalWatcher = new CalcWatcher(
                new CalculateInterface() {
                    @Override
                    public void Calculate() {
                        double total = binder.cstotal.getValue()
                                - binder.vendorterminaltotal.getValue();

                        binder.savings1month.setValue(total);

                        double percentageIncrease = (total / binder.cstotal
                                .getValue()) * 100;

                        double value = Double.isNaN(percentageIncrease)
                                || Double.isInfinite(percentageIncrease) ? 0.00
                                : percentageIncrease;

                        binder.savingsPercentage.setValue(value);
                    }
                });

        binder.cstotal.Ctrl.addTextChangedListener(oneMonthTotalWatcher);

        binder.vendorterminaltotal.Ctrl
                .addTextChangedListener(oneMonthTotalWatcher);

        TextWatcher periodTotalWatcher = new CalcWatcher(
                new CalculateInterface() {
                    @Override
                    public void Calculate() {
                        double total1year = binder.savings1month.getValue() * 12;

                        binder.savings1year.setValue(total1year);

                        double total4years = binder.savings1month.getValue() * 48;

                        binder.savings4years.setValue(total4years);
                    }
                });

        binder.savings1month.Ctrl.addTextChangedListener(periodTotalWatcher);

        // Inflate the layout for this fragment
        return view;
    }
}
