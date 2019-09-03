package dev.felicity.felicitycbt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class TermsAndConditions extends AppCompatActivity {

    private Button mAcceptButton;
    private Button mDeclineButton;
    private HashMap<String, Object> mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");


        mAcceptButton = (Button)findViewById(R.id.yesBtn);
        mDeclineButton = (Button) findViewById(R.id.noBtn);


        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(TermsAndConditions.this,
                        Login.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

        mDeclineButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}
