package com.avantics.savingscalcpremium.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.avantics.savingscalcpremium.fragments.SettingsFragment;

public class SettingsActivity extends PreferenceActivity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}
}
