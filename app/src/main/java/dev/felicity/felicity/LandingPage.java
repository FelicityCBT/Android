package dev.felicity.felicity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class LandingPage extends AppCompatActivity {


    private Button mSessionButton;
    private Button mSignout;
    private Button mPHQ9;
    private Button mBlankSessionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mSessionButton = findViewById(R.id.session);
        mSignout=findViewById(R.id.logout);
        mPHQ9=findViewById(R.id.phq9);
        mBlankSessionButton=findViewById(R.id.blankSession);

        mSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(LandingPage.this, CbtIntro.class);
                startActivity(intentLoadNewActivity);
            }
        });

        mPHQ9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(LandingPage.this, PHQ9.class);
                startActivity(intentLoadNewActivity);
            }
        });

       mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
                Intent intentLoadNewActivity = new Intent(LandingPage.this, Login.class);
                intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLoadNewActivity);
                finish();
            }
        });

       mBlankSessionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intentLoadNewActivity = new Intent(LandingPage.this, BlankSession.class);
               startActivity(intentLoadNewActivity);
           }
       });
    }
}
