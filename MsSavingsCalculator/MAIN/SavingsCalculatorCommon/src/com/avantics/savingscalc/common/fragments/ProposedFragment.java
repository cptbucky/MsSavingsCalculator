package com.avantics.savingscalc.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avantics.common.IBindManager;
import com.avantics.savingscalc.common.R;

public class ProposedFragment extends Fragment {
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.quote_proposed, container, false);

        IBindManager binder = (IBindManager)getActivity();

        binder.AttachToView(view);

        return view;
	}
}
