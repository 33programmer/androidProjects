package com.example.macbookpro.stockmarketyahootutorial;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;

/**
 * Created by MacBookPro on 6/7/16.
 *
 * This class will
 * make the functionality of the RefreshButton
 * Receives the information from the Stock Search Query
 * Communicates with the YAHOO Finance API via a internet connection
 */
public class SelectedItemActivity extends AppCompatActivity{


    TextView titleTextView, bodyTextView;
    ImageView imageCategory;
    ImageButton refresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // reference to the layout file
        setContentView(R.layout.selected_item);

        titleTextView = (TextView)findViewById(R.id.SelectedItemTitle);
        bodyTextView = (TextView)findViewById(R.id.SelectedItemBody);
        imageCategory = (ImageView)findViewById(R.id.SelectedItemImage);


        // Refresh Button initializer

        if(isOnline())
        {

            // We must retrieve the information from the MainActivity as search stock key.

            String symbol;
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                return;
            }

            // Retrieving the stock symbol

            String qString = extras.getString("searchStock");

            if(qString == null || qString.equals(""))
            {
                // When we start search for the stocks via query

                symbol = getIntent().getStringExtra(MainActivityFragment.EXTRA_MESSAGE);
                titleTextView.setText(getIntent().getStringExtra(MainActivityFragment.EXTRA_TITLE));
                imageCategory.setImageResource(getIntent().getIntExtra(MainActivityFragment.EXTRA_CATEGORY, 0));
            }else{
                symbol = qString;
            }



            // query is * and symbol is GOOG


            // This is a url that will give information about the stock as a json object.
            //     http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22GOOG%22%29&env=store://datatables.org/alltableswithkeys&format=json";

            String query = "*";
            String url = "http://query.yahooapis.com/v1/public/yql?q=select%20" + query + "%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22"+ symbol +"%22%29&env=store://datatables.org/alltableswithkeys&format=json";

            new JSONTask().execute(url);



        }else{
            // if no internet connection

            Toast.makeText(getApplicationContext(), "The internet connection is down.", Toast.LENGTH_SHORT).show();


        }


    }

    /**
     * This method was added 3-17-2017. Thanks for calling it out Rauhmel.
     * @return boolean
     */
    private boolean isOnline() {

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());

    }


    public class JSONTask extends AsyncTask<String, String, String> {


        // This is for the stock assessment. If the stock has gain or lost value.
        boolean green = false;

        // Assume that the user has not a value. So empty is true.
        boolean empty = true;


        // this is the name of the stock
        String stock_name;





        @Override
        protected String doInBackground(String... params) {


            // Configuring the connection to be null and the Buffered Reader likewise.
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {



                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));


                StringBuffer buffer = new StringBuffer();

                String line;

                while((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }


                String entireJSON = buffer.toString();


                //  Begin to take JSON objects and JSON strings

                JSONObject formattedJSON = new JSONObject(entireJSON);
                StringBuffer all_info = new StringBuffer();



              JSONObject jsonObject = formattedJSON.getJSONObject("query").getJSONObject("results").getJSONObject("quote");

                String stock_value = jsonObject.getString("Ask");
                String name = jsonObject.getString("Name");
                double stock_change_value = jsonObject.getDouble("Change");
                String currency = jsonObject.getString("Currency");

                double price_double = 0.0;




                if(stock_value != null && !stock_value.equals("") && !stock_value.equals("null") ){
                    price_double = Double.parseDouble(stock_value);

                    // We get a stock value that is not empty, null, nor "null". then we are not empty.
                    empty = false;
                }else{
                    empty = true;
                }



                // Used to make the numbers appear as monetary values

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();


                stock_name = name;
                String sell;


                if(stock_change_value < 0) {
                    green = false;
                    sell = "This stock has depreciated in its original value. \n If you had 100 shares of this company, you would have lost: " + currencyFormat.format(stock_change_value * 100) + " " + currency;
                }
                else{
                    green = true;
                    sell = "This stock has gained in its original value. \n If you had 100 shares of this company, you would have gained: " + currencyFormat.format(stock_change_value * 100) + " " + currency;


                }


                String stock_summary = "\n\n" + stock_name +"'s current stock is worth: " + currencyFormat.format(price_double) + " " + currency;
                stock_summary += "\n\n" + sell;


                // If we !empty 'which means not empty' == true, then return a stock summary.
                if(!empty){
                    // empty = false; this is redundant.
                    return stock_summary;
                }




            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            if(!empty) {
                if (!green) {
                    imageCategory.setImageResource(R.drawable.red);
                } else {
                    imageCategory.setImageResource(R.drawable.green);
                }

                titleTextView.setText(stock_name);
                bodyTextView.setText(s);
            }else {
                bodyTextView.setText("Nothing was found. This Stock Symbol does not exist. Enter in something like this STJ");

            }




        }
    }


    public void RefreshButton(View view){

        Toast.makeText(getApplicationContext(), "Thanks for refreshing!", Toast.LENGTH_SHORT).show();



        String symbol;
        Bundle extras = getIntent().getExtras();
        String qString = extras.getString("searchStock");





        if(qString == null || qString.equals(""))
        {
            symbol = getIntent().getStringExtra(MainActivityFragment.EXTRA_MESSAGE);

        }else {
            symbol = qString;
        }


        String query = "*";
        String url = "http://query.yahooapis.com/v1/public/yql?q=select%20" + query + "%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22"+ symbol +"%22%29&env=store://datatables.org/alltableswithkeys&format=json";

        new JSONTask().execute(url);
    }



}

