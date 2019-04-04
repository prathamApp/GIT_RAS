package com.pratham.readandspeak.ui.reading_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pratham.readandspeak.R;
import com.pratham.readandspeak.modalclasses.CreditsModal;

import java.util.ArrayList;

/**
 * Created by Pravin on 04-May-18.
 */

public class AcknowledgementAdapter extends ArrayAdapter<CreditsModal> {

    public AcknowledgementAdapter(Context context, ArrayList<CreditsModal> listForAdapter) {
        super(context,0,listForAdapter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CreditsModal usage = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ack_row, parent, false);
        }
        // Lookup view for data population
        TextView ack_title =convertView.findViewById(R.id.ack_title);
        TextView ack_data =convertView.findViewById(R.id.ack_data);
        // Populate the data into the template view using the data object

        ack_title.setText(usage.contentName);
        ack_data.setText(usage.contentData);
        // Return the completed view to render on screen
        return convertView;
    }
}