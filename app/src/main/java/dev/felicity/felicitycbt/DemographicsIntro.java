package dev.felicity.felicitycbt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class DemographicsIntro extends AppCompatActivity {

    private HashMap<String, Object> mInfo;
    private Button mContinueButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics_intro);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mContinueButton = findViewById(R.id.yesBtn);
        mCancelButton = findViewById(R.id.noBtn);

        // Continue to Demographics
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(DemographicsIntro.this, Demographics.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

        // Go to LandingPage
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(DemographicsIntro.this, Login.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

    }
}
