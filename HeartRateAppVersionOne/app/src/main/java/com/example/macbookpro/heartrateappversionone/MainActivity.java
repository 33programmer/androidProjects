package com.example.macbookpro.heartrateappversionone;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/*
* The Manifest File:
* android:screenOrientation="portrait"
            android:windowSoftInputMode="stateAlwaysVisible"
                theme: @style/AppTheme

           Source:     http://www.heart.org/HEARTORG/HealthyLiving/PhysicalActivity/FitnessBasics/Target-Heart-Rates_UCM_434341_Article.jsp#.V4mU95MrK34
* */

public class MainActivity extends Activity {

    //This is needed so that we have a percent displayed when the number is shown so .73 is shown as 73$
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    // This is needed so that we don't have some many numbers after the 100th place. So instead of 543.225445 we have 543.22
    private static final DecimalFormat decimalformat = new
            DecimalFormat("###.00");

    // This is the default age for the person.
    private int age = 0;


    // This is the default percent for the SeekBar. Even though it as a decimal number, decimalFormat will change into a percent.
    private double customPercent = 0.60; // initial custom percent

    // The ageTextView is used to render the number back to the user. Better to this than use the Edit Text

    private TextView ageTextView;
    private TextView hr50TV;  // This TV is not changed at all. It is used to place the 50% from Strings resource file
    private TextView hrCustomTV;  // This will change as the hr Percent will change with the SeekBar and TextWatcher

    private TextView percentCustomTV; // this changes according to the seek bar
   /// not needed  private TextView fiftyTV; // this does not change


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        // This is overlapping the ageInput Edit Text
        ageTextView = (TextView) findViewById(R.id.ageTextView);

        // tV display for 50 %
        hr50TV = (TextView)findViewById(R.id.hr50TextView);

        // look above
        hrCustomTV = (TextView)findViewById(R.id.hrCustomTextView);

        // percent custom text i.e 60 % or
        percentCustomTV = (TextView)findViewById(R.id.percentCustomTextView);

        // just says 50% and does not change fiftyTV = (TextView)findViewById(R.id.percent50TextView); so we don't need it.

        // explicitly stating that age is the inserted text of TextView. setText Only accepts strings
        ageTextView.setText(String.valueOf(age));
        updateStandard(); // update the 50% heart rate TextViews. Standard refers to the base heart rate, theMinimum
        updateCustom(); // update the custom tip TextViews





        // set amountEditText's TextWatcher. This allows us to act upon the user's input
        EditText ageEditText = (EditText) findViewById(R.id.ageInput);
        ageEditText.addTextChangedListener(ageEditTextWatcher);


        // set customTipSeekBar's OnSeekBarChangeListener
        SeekBar customTipSeekBar =
                (SeekBar) findViewById(R.id.customSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);


    }

    // updates 50%  TextView. is involved! Standard is 50.
    // Math for the HR. 220 - age = HR. Target is to between 50% (.50) and 85% of HR. Source: American Heart Association
    private void updateStandard()
    {
       // maximum heart rate
        double maximumHeartRate = 220 - age;

        double MHR50 = maximumHeartRate * 0.50;

        // update the hr50TV
        hr50TV.setText(String.valueOf(decimalformat.format(MHR50)));


    } // end method updateStandard


    // update Custom

    // updates the custom is what ever they prefer
    private void updateCustom() {
        // show customPercent in percentCustomTextView formatted as %
        percentCustomTV.setText(percentFormat.format(customPercent));


        // maximum heart rate
        double maximumHeartRate = 220 - age;

        double MHRCustom = maximumHeartRate * customPercent;

        hrCustomTV.setText(String.valueOf(decimalformat.format(MHRCustom)));

    } // end method updateCustom

    // called when the user changes the position of SeekBar
    // This is an interface
    private SeekBar.OnSeekBarChangeListener customSeekBarListener =
            new SeekBar.OnSeekBarChangeListener()
            {
                // update customPercent, then call updateCustom
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    // sets customPercent to position of the SeekBar's thumb
                    customPercent = progress / 100.0;
                    updateCustom(); // update the custom tip TextViews
                } // end method onProgressChanged

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                } // end method onStartTrackingTouch

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                } // end method onStopTrackingTouch
            }; // end OnSeekBarChangeListener



    private TextWatcher ageEditTextWatcher = new TextWatcher()
        {
            // called when the user enters a number
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                //
                try
                {
                    age = Integer.parseInt(s.toString());
                } // end try
                catch (NumberFormatException e)
                {
                    age = 0; // default if an exception occurs
                } // end catch

                // display currency formatted bill amount
                ageTextView.setText(String.valueOf(age));
                updateStandard(); // update the 15% tip TextViews
                updateCustom(); // update the custom tip TextViews
            } // end method onTextChanged

            @Override
            public void afterTextChanged(Editable s)
            {
            } // end method afterTextChanged

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {
            } // end method beforeTextChanged
        }; // end amountEditTextWatcher


    } // end of MainActivity


