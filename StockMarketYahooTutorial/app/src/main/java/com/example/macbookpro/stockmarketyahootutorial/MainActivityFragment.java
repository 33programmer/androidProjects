package com.example.macbookpro.stockmarketyahootutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by MacBookPro on 6/6/16.
 *
 *
 *This class creates the Stocks explicitly by adding them to an arraylist.
 * This class is based upon the ListItemsAdapterClass
 */
public class MainActivityFragment extends ListFragment {



    // Identifier for the Class(Selected Item Activity Class)
    private static String PACKAGE_NAME = "com.example.macbookpro.stockmarketyahootutorial";

    public final static String EXTRA_TITLE = PACKAGE_NAME + ".title";
    public final static String EXTRA_MESSAGE = PACKAGE_NAME + ".message";
    public final static String EXTRA_CATEGORY = PACKAGE_NAME + ".category";


    // Create an instance from the ListItemsAdapter Class because the stock must be displayed in the row_layout xml file
    private ArrayList<ListItems> items;
    private ListItemsAdapter itemsAdapter;



    // When the application starts, it should call this method
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        items = new ArrayList<ListItems>();


        items.add(new ListItems("Google's Stock", "GOOG", ListItems.Category.FINANCE));
        items.add(new ListItems("Facebook's Stock", "FB", ListItems.Category.SOCIAL));
        //items.add(new ListItems("St. Jude Medical's Stock", "STJ", ListItems.Category.MEDICAL));


        itemsAdapter = new ListItemsAdapter(getActivity(), items);
        setListAdapter(itemsAdapter);




    }


    // Method that is called when the stock is clicked
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), SelectedItemActivity.class);

        ListItems item = (ListItems)getListAdapter().getItem(position);

        intent.putExtra(EXTRA_TITLE, item.getTitle());
        intent.putExtra(EXTRA_MESSAGE, item.getMessage());
        intent.putExtra(EXTRA_CATEGORY, item.getCategory());


        startActivity(intent);


    }
}
