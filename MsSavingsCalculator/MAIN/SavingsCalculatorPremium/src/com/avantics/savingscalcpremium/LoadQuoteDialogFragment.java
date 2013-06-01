package com.avantics.savingscalcpremium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LoadQuoteDialogFragment extends DialogFragment {
//	private Quote[] currentQuotes = null;
	public String SelectedQuoteName = null;
	
	public LoadQuoteDialogFragment() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String[] arguments = getArguments().getStringArray("fileNames");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.load_quote_dialog_title)
				.setItems(arguments, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index
						// position
						// of the selected item
						
						com.avantics.savingscalcpremium.activities.MainActivity activity = (com.avantics.savingscalcpremium.activities.MainActivity)getActivity();
						
						activity.setSelectedQuote(which);
					}
				});
		
		return builder.create();
	}
}