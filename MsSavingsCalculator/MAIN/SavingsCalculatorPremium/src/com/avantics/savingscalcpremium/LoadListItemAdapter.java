package com.avantics.savingscalcpremium;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class LoadListItemAdapter extends ArrayAdapter<LoadListItem> {
	ArrayList<DeleteQuoteRequestHandler> deleteQuoteObservers = new ArrayList<DeleteQuoteRequestHandler>();

	Context context;
	int layoutResourceId;
	ArrayList<LoadListItem> data = null;

	public LoadListItemAdapter(Context context, int layoutResourceId,
			ArrayList<LoadListItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		LoadListItemHolder holder = null;

		LoadListItem listItem = data.get(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new LoadListItemHolder();
			holder.imgButton = (ImageButton) row.findViewById(R.id.deleteQuote);
			holder.imgButton.setTag(listItem);
			holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

			row.setTag(holder);
		} else {
			holder = (LoadListItemHolder) row.getTag();
		}

		holder.txtTitle.setText(listItem.title);
		holder.imgButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoadListItem item = (LoadListItem) v.getTag();

				for (DeleteQuoteRequestHandler observer : deleteQuoteObservers) {
					observer.callback(item);
				}
			}
		});

		return row;
	}

	public void setOnDeleteQuoteRequested(DeleteQuoteRequestHandler observer) {
		deleteQuoteObservers.add(observer);
	}

	static class LoadListItemHolder {
		ImageButton imgButton;
		TextView txtTitle;
	}
}
