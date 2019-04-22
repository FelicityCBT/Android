package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import java.util.HashMap;

public class Gratuity extends AppCompatActivity {

    private Button mContinue;
    private Button mExit;
    private HashMap<String,Object> mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gratuity);


        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mContinue=findViewById(R.id.con);
        mExit=findViewById(R.id.exit);



        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Gratuity.this, ThoughtSituation.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Gratuity.this, GratuityLog.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}
