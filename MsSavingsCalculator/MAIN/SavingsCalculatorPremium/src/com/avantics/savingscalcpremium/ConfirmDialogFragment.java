package com.avantics.savingscalcpremium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import java.util.ArrayList;

public class ConfirmDialogFragment extends DialogFragment {
	ArrayList<ConfirmationDialogHandler> confirmationObservers = new ArrayList<ConfirmationDialogHandler>();
	
	public ConfirmDialogFragment() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String titleString = getArguments().getString("titleString");
		final String confirmString = getArguments().getString("confirmString");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(titleString)
				.setPositiveButton(confirmString, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						for (ConfirmationDialogHandler observer : confirmationObservers) {
							observer.callback();
						}
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Do nothing.
							}
						});
		
		return builder.create();
	}
	
	public void setOnConfirmed(ConfirmationDialogHandler observer) {
		confirmationObservers.add(observer);
	}
}