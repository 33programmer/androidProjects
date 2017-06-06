package com.example.macbookpro.stockmarketyahootutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 *
 *
 * Created by MacBookPro on 6/6/16.
 */
public class ListItemsAdapter extends ArrayAdapter<ListItems> {
    public ListItemsAdapter(Context context, List<ListItems> items) {
        super(context, 0, items);
    }


    // Returns information about the current list stock item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get the data for this position
        ListItems note = getItem(position);

        // Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }




        // Grab references of views so we can populate them with specific note row data

        TextView itemTitle = (TextView)convertView.findViewById(R.id.list_item_title);
        TextView itemText = (TextView)convertView.findViewById(R.id.listItemBody);
        ImageView itemImage = (ImageView)convertView.findViewById(R.id.listItemImage);

        // Denote where to receive information


        itemTitle.setText(note.getTitle());
        //itemText.setText(note.getMessage());
        itemImage.setImageResource(note.getAssociatedDrawable());




        return convertView;
    }
}
