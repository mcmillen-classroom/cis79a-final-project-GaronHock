package com.garonhock.dontdrinkanddrivesilly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mHWButton;

    public Button mBACButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHWButton = (Button) findViewById(R.id.HWButton);
        mBACButton = (Button) findViewById(R.id.BACButton);
        mHWButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.HWButton) {
            Intent HeightWeightIntent = HeightWeightActivity.newIntent(this, this);
            startActivity(HeightWeightIntent);
        }

    }
}

