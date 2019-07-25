package com.garonhock.dontdrinkanddrivesilly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uber.sdk.android.rides.RideRequestButton;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.rides.client.SessionConfiguration;

import java.text.DecimalFormat;

public class BACResultActivity extends AppCompatActivity {



    private LinearLayout mBACLayout;
    private TextView mBACTextView;

    private Button mContactsButton;
    public static final String EXTRA_BAC_RESULT = "bac_result";
    private final int PICK_CONTACT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactsButton = (Button) findViewById(R.id.btnload);

        setContentView(R.layout.activity_bacresult);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.BACResult_Layout);
        final AnimationDrawable drawable = new AnimationDrawable();
        final Handler handler = new Handler();

        drawable.addFrame(new ColorDrawable(Color.RED), 400);
        drawable.addFrame(new ColorDrawable(Color.BLUE), 400);
        drawable.setOneShot(false);

        layout.setBackgroundDrawable(drawable);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        }, 100);
        //  mBACLayout = (LinearLayout) findViewById(R.id.BACResult_Layout);
        mBACTextView = (TextView) findViewById(R.id.BAC_TEXT_VIEW);
        Intent launchIntent = getIntent();
        double BACResult = launchIntent.getDoubleExtra(EXTRA_BAC_RESULT, 0.0);
        mBACTextView.setText(String.valueOf(BACResult));
        DecimalFormat betterDouble = new DecimalFormat("#.###");
        mBACTextView.setText(betterDouble.format(BACResult));

        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("<CLIENT_ID>")
                // required for enhanced button features
                .setServerToken("<TOKEN>")
                // required for implicit grant authentication
                .setRedirectUri("<REDIRECT_URI>")
                // optional: set sandbox as operating environment
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();
        UberSdk.initialize(config);

    }


    public static Intent newIntent(Context ctx, HeightWeightActivity heightWeightActivity) {
        Intent ret = new Intent(ctx, BACResultActivity.class);
        ret.putExtra(EXTRA_BAC_RESULT, heightWeightActivity.getBACDouble());
        return ret;


    }

    public void callContacts(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data){
        super.onActivityResult(reqCode,resultCode,data);

        if(reqCode == PICK_CONTACT){
            if(resultCode == AppCompatActivity.RESULT_OK){
                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);

                if(c.moveToFirst()){
                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    Toast.makeText(this, "You've Picked: " + name, Toast.LENGTH_LONG).show();
                }
            }
        }

    }


}

