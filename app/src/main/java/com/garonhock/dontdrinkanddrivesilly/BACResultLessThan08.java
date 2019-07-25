package com.garonhock.dontdrinkanddrivesilly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tomer.fadingtextview.FadingTextView;

import java.text.DecimalFormat;

import static com.garonhock.dontdrinkanddrivesilly.R.*;
import static com.garonhock.dontdrinkanddrivesilly.R.id.*;


public class BACResultLessThan08<mIndex> extends AppCompatActivity {

    public static final String EXTRA_LESS_THAN_08_RESULTS= "bac_less_than_08_results";
    private TextView mLessThan08TextView;
    private FadingTextView mFadingTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_bacresultlessthan08);
        Intent launchIntent = getIntent();

        mFadingTextView = (FadingTextView) findViewById(fadingTextView);
        mLessThan08TextView = (TextView) findViewById(lessthan08);

        double BACResultLessThan08 = launchIntent.getDoubleExtra(EXTRA_LESS_THAN_08_RESULTS, 0.0);
        mLessThan08TextView.setText(String.valueOf(BACResultLessThan08));
        DecimalFormat betterDouble = new DecimalFormat("#.###");
        mLessThan08TextView.setText(betterDouble.format(BACResultLessThan08));
    }

    public static Intent newIntent(Context ctx, HeightWeightActivity heightWeightActivity) {
        Intent ret = new Intent(ctx, BACResultLessThan08.class);
        ret.putExtra(EXTRA_LESS_THAN_08_RESULTS, heightWeightActivity.getBACDouble());
        return ret;
    }
}
