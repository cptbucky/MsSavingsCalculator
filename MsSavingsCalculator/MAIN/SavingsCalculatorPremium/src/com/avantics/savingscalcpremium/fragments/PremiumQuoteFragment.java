package com.avantics.savingscalcpremium.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avantics.savingscalc.common.R;
import com.avantics.savingscalc.common.fragments.QuoteFragment;

public class PremiumQuoteFragment extends QuoteFragment {

	TextView lblVendorRate;
	TextView lblVendorIncPci;
	TextView lblVendorTerminal;
	
	SharedPreferences sp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = super.onCreateView(inflater, container, savedInstanceState);

		lblVendorRate = (TextView) view.findViewById(R.id.lblVendorRate);
		lblVendorIncPci = (TextView) view.findViewById(R.id.lblVendorIncPci);
		lblVendorTerminal = (TextView) view.findViewById(R.id.lblVendorTerminal);

		sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity().getApplicationContext());

		updateLabelsWithUserSettings();

//		sp.registerOnSharedPreferenceChangeListener(this);
		
		return view;
	}

	private void updateLabelsWithUserSettings() {
		String vendorName = sp.getString(
				SettingsFragment.PREF_BRANDING_VENDOR_NAME, "");
		
		if (!vendorName.equals("")) {
			lblVendorRate.setText(getResources().getText(R.string.vendor_rate).toString()
					.replace("Vendor", vendorName));
			lblVendorIncPci.setText(getResources().getText(R.string.vendor_inc_pci).toString()
					.replace("Vendor", vendorName));
			lblVendorTerminal.setText(getResources().getText(R.string.vendor_terminal).toString()
					.replace("Vendor", vendorName));
		}
	}

//	@Override
//	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
//			String key) {
//		if (key.equals("pref_branding_vendor_name")) { // this is premium
//													// functionality!!
//			updateLabelsWithUserSettings(sharedPreferences.getString(key, ""));
//		}
//	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    updateLabelsWithUserSettings();
	}
}
