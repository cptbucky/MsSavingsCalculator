package com.avantics.savingscalc.common.fragments;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.avantics.savingscalc.common.R;

public class QuoteFragment extends Fragment {

    TextView lblVendorHeader;
    TextView lblVendorIncPci;
    TextView lblVendorTerminal;
    TextView lblVendorTotal;

    SharedPreferences sp;

    public static String VENDOR_HEADER_LABEL;
    public static String VENDOR_INCPCI_LABEL;
    public static String VENDOR_TERMINAL_LABEL;
    public static String VENDOR_TOTAL_LABEL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main, container, false);

        lblVendorHeader = (TextView) view.findViewById(R.id.vendorHeaderLabel);
        lblVendorIncPci = (TextView) view.findViewById(R.id.lblVendorIncPci);
        lblVendorTerminal = (TextView) view.findViewById(R.id.lblVendorTerminal);
        lblVendorTotal = (TextView) view.findViewById(R.id.lblVendorTotal);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        updateLabelsWithUserSettings();

        return view;
    }

    private void updateLabelsWithUserSettings() {
        String vendorName = getVendorString();

        //setting these up as other parts of the system want them
        VENDOR_HEADER_LABEL = getResources().getText(R.string.vendor_section_header).toString()
                .replace("Vendor", vendorName);
        VENDOR_INCPCI_LABEL = getResources().getText(R.string.vendor_inc_pci).toString()
                .replace("Vendor", vendorName);
        VENDOR_TERMINAL_LABEL = getResources().getText(R.string.vendor_terminal).toString()
                .replace("Vendor", vendorName);
        VENDOR_TOTAL_LABEL = getResources().getText(R.string.vendor_statement_total).toString()
                .replace("Vendor", vendorName);

        if (!vendorName.equals("Vendor")) {
            if (lblVendorHeader != null) lblVendorHeader.setText(VENDOR_HEADER_LABEL);
            if (lblVendorIncPci != null) lblVendorIncPci.setText(VENDOR_INCPCI_LABEL);
            if (lblVendorTerminal != null) lblVendorTerminal.setText(VENDOR_TERMINAL_LABEL);
            if (lblVendorTotal != null) lblVendorTotal.setText(VENDOR_TOTAL_LABEL);
        }
    }

    private String getVendorString() {
        String vendorName = sp.getString(SettingsFragment.PREF_BRANDING_VENDOR_NAME, "Vendor");

        return vendorName.equals("") ? "Vendor" : vendorName; // wrong way to do it, should be on the prefs
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String vendorName = getVendorString();

        final ActionBar aBar = getActivity().getActionBar();

        if (aBar.getTabCount() > 0) {
            ActionBar.Tab proposedTab = aBar.getTabAt(1);

            proposedTab.setText(vendorName);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        updateLabelsWithUserSettings();
    }
}
