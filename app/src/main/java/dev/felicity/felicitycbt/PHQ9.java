package dev.felicity.felicitycbt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;


public class PHQ9 extends AppCompatActivity {

    private RadioGroup[] mRadioGroups= new RadioGroup[10];
    private Button mSubmit;
    private Button mCancel;
    private HashMap<String, Object> mInfo;
    private int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phq9);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mSubmit=findViewById(R.id.submit);
        mCancel=findViewById(R.id.cancel);

        mRadioGroups[0]=findViewById(R.id.rg1);
        mRadioGroups[1]=findViewById(R.id.rg2);
        mRadioGroups[2]=findViewById(R.id.rg3);
        mRadioGroups[3]=findViewById(R.id.rg4);
        mRadioGroups[4]=findViewById(R.id.rg5);
        mRadioGroups[5]=findViewById(R.id.rg6);
        mRadioGroups[6]=findViewById(R.id.rg7);
        mRadioGroups[7]=findViewById(R.id.rg8);
        mRadioGroups[8]=findViewById(R.id.rg9);
        mRadioGroups[9]=findViewById(R.id.rg1);


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                boolean error= false;

                for(int i=0; i<9;i++){ // PHQ Questions
                    RadioGroup group= mRadioGroups[i];
                    if (group.getCheckedRadioButtonId() == -1)
                    {
                        Toast.makeText(PHQ9.this,"All questions must be answered",Toast.LENGTH_LONG).show();
                        error=true;
                        break;
                    }
                    int q_score=group.indexOfChild(findViewById(group.getCheckedRadioButtonId()));
                    // TODO: Fix Prefer Not To Say --> If user says this, they are ineligible for the study
                    // Check if "Prefer Not to Say was checked
                    if(q_score == 4) q_score = 0;
                    score = score + q_score;
                    String key = "PHQ9 question " + i;
                    mInfo.put(key, q_score); 
                }

                if(!error){
                    // Continue to GAD
                    Intent intentLoadNewActivity = new Intent(PHQ9.this, GAD.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    intentLoadNewActivity.putExtra("PHQScore", (Integer)score);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(PHQ9.this, LandingPage.class); // TODO: Change to accommodate new LandingPages
                startActivity(intentLoadNewActivity);
                finish();
            }
        });
    }

}