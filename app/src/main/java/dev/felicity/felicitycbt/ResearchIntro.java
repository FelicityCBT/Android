package dev.felicity.felicitycbt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class ResearchIntro extends AppCompatActivity {

    private HashMap<String, Object> mInfo;
    private Button mAcceptButton;
    private Button mDeclineButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_intro);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mAcceptButton = findViewById(R.id.yesBtn);
        mDeclineButton = findViewById(R.id.noBtn);

        // User wants to hear more about Research Study
        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(ResearchIntro.this, Consent.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

        // User does not want to hear about Research Study
        // TODO: Accommodate for different landing pages
        mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(ResearchIntro.this, LandingPage.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

    }
}
