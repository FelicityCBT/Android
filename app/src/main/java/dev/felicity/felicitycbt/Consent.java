package dev.felicity.felicitycbt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Consent extends AppCompatActivity {

    private HashMap<String, Object> mInfo;
    private Button mAcceptButton;
    private Button mDeclineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mAcceptButton = findViewById(R.id.yesBtn);
        mDeclineButton = findViewById(R.id.noBtn);

        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInfo.put("ConsentForm", "Yes");
                Intent intentLoadNewActivity = new Intent(Consent.this, DemographicsIntro.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                popDialog(true, intentLoadNewActivity);
            }
        });

        mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInfo.put("ConsentForm", "No");
                Intent intentLoadNewActivity = new Intent(Consent.this, LandingPage.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                popDialog(false, intentLoadNewActivity);
            }
        });

    }

    // Show Dialog concerning mental health
    public void popDialog(final boolean isAccept, final Intent intent) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Please Read");
        alertDialogBuilder.setMessage(R.string.warningPopUp);
        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if(isAccept) { // User consented, so continue to DemographicsIntro Screen
                    startActivity(intent);

                } else { // User did not consent, so continue to LandingPage
                    startActivity(intent);
                }
            }
        });

        alertDialogBuilder.create().show();
    }
}
