package com.avantics.savingscalc.common;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.avantics.savingscalc.common.*;

import java.util.ArrayList;

public class ChooseEmailItemAdapter extends ArrayAdapter<LoadListItem> {

    Context context;
    int layoutResourceId;
    ArrayList<LoadListItem> data = null;

    public ChooseEmailItemAdapter(Context context, int layoutResourceId,
                                  ArrayList<LoadListItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChooseEmailItemHolder holder = null;

        LoadListItem listItem = data.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ChooseEmailItemHolder();

            holder.txtTitle = (TextView) row.findViewById(com.avantics.savingscalc.common.R.id.txtTitle);

            row.setTag(holder);
        } else {
            holder = (ChooseEmailItemHolder) row.getTag();
        }

        holder.txtTitle.setText(listItem.title);

        return row;
    }

    static class ChooseEmailItemHolder {
        TextView txtTitle;
    }
}
