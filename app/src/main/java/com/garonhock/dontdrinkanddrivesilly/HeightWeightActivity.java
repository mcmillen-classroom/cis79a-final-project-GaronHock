package com.garonhock.dontdrinkanddrivesilly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class HeightWeightActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_BAC_RESULTS = 0;
    private static final int REQUEST_LESS_THAN08_RESULTS = 1;
    private LinearLayout mBMILayout;
    public RadioGroup mMFRadioButtons;
    private RadioButton mMaleRadioButton;
    public RadioButton mFemaleRadioButton;
    public EditText mWeightEditText;
    public EditText mDrinkEditText;
    public EditText mHoursEditText;
    public Button mBACButton;

    private double weightDouble, drinkDouble, timeDouble, genderConstant, BACDouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height_weight);

        Intent launchIntent = getIntent();

        mBMILayout = (LinearLayout) findViewById(R.id.BMI_Layout);

        mMFRadioButtons = (RadioGroup) findViewById(R.id.MFRadioButtons);
        mMaleRadioButton = (RadioButton) findViewById(R.id.MaleRadioButton);
        mFemaleRadioButton = (RadioButton) findViewById(R.id.FemaleRadioButton);

        mWeightEditText = (EditText) findViewById(R.id.WeightEditText);
        mDrinkEditText = (EditText) findViewById(R.id.DrinkEditText);
        mHoursEditText = (EditText) findViewById(R.id.HoursEditText);

        mBACButton = (Button) findViewById(R.id.BACButton);


        mMFRadioButtons.setOnClickListener(this);
        mMaleRadioButton.setOnClickListener(this);
        mFemaleRadioButton.setOnClickListener(this);
        mWeightEditText.setOnClickListener(this);
        mDrinkEditText.setOnClickListener(this);
        mHoursEditText.setOnClickListener(this);
        mBACButton.setOnClickListener(this);


        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.MFRadioButtons);

                genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.MaleRadioButton:
                                // do operations specific to this selection
                                genderConstant = 0.73;
                                break;
                            case R.id.FemaleRadioButton:
                                // do operations specific to this selection
                                genderConstant = 0.66;
                                break;


                        }
                    }
                });
            }





    public static Intent newIntent(Context ctx, MainActivity mainActivity) {
        Intent ret = new Intent(ctx, HeightWeightActivity.class);
        return ret;
    }



    @Override
    public void onClick(View view) {
         if (mMFRadioButtons.getCheckedRadioButtonId() == -1|(mWeightEditText.getText().toString().equals("") | mDrinkEditText.getText().toString().equals("")
                | mHoursEditText.getText().toString().equals(""))) {
            Toast.makeText(HeightWeightActivity.this, "Cannot leave fields blank", Toast.LENGTH_SHORT).show();

        } else {
            weightDouble = Double.parseDouble(mWeightEditText.getText().toString());//converts text into a double
            drinkDouble = Double.parseDouble(mDrinkEditText.getText().toString());
            timeDouble = Double.parseDouble(mHoursEditText.getText().toString());

            BACDouble = (((drinkDouble * 12 * 0.07) *5.14)/(weightDouble * genderConstant)) - (0.015 * timeDouble);

            DecimalFormat betterDouble = new DecimalFormat("#.##");




            if (view.getId() == R.id.BACButton && (mWeightEditText != null) && (mDrinkEditText != null)
                    && (mHoursEditText != null) && (BACDouble >= 0.08)){
                Intent BACResultIntent = new Intent(getApplicationContext(), BACResultActivity.class);
                BACResultIntent.putExtra("bac_result", BACDouble); // THIS LINE IS WHAT I WAS MISSING
                startActivity(BACResultIntent);

            }
            else if (view.getId() == R.id.BACButton && (mWeightEditText != null) && (mDrinkEditText != null)
                    && (mHoursEditText != null) && (BACDouble <= 0.079)){
                Intent BacResultLessThan08Intent = new Intent(getApplicationContext(), BACResultLessThan08.class);
                BacResultLessThan08Intent.putExtra("bac_less_than_08_results", BACDouble);
                startActivity(BacResultLessThan08Intent);

            }

        }
    }

    public double getBACDouble() {
        return BACDouble;
    }
}








