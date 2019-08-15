package dev.felicity.felicitycbt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class GAD extends AppCompatActivity {

    private RadioGroup[] mRadioGroups= new RadioGroup[8];
    private Button mSubmit;
    private Button mCancel;
    private HashMap<String, Object> mInfo;
    private int score = 0;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gad);


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


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                boolean error= false;

                for(int i=0; i<8;i++){ // GAD Questions
                    RadioGroup group= mRadioGroups[i];
                    if (group.getCheckedRadioButtonId() == -1)
                    {
                        Toast.makeText(GAD.this,"All questions must be answered",Toast.LENGTH_LONG).show();
                        error=true;
                        break;
                    }
                    int q_score=group.indexOfChild(findViewById(group.getCheckedRadioButtonId()));
                    if(q_score == 4) q_score = 0; // "Prefer Not To Say" is scored as a 0
                    score = score + q_score;
                    String key= "GAD7 question "+i;
//                    mInfo.put(key, q_score);
                }

                if(!error){

//                    String ct = ""+score;
//                    //encryption/decryption
//                    String uid= FirebaseAuth.getInstance().getUid();
//                    try {
//                        ct= EncUtil.encryptMsg(""+score, uid);
//                        Toast.makeText(GAD.this,ct,Toast.LENGTH_LONG).show();
//                        String uct=EncUtil.decryptMsg(ct, uid);
//                        //Toast.makeText(GAD.this,uct,Toast.LENGTH_LONG).show();
//                    }
//                    catch(Exception e){
//                        Toast.makeText(GAD.this,e.toString(),Toast.LENGTH_LONG).show();
//                    }

                    //for db
                    // mInfo.put("TotalScorePHQ", EncUtil.encryptMsg(""+score, uid)); TODO: Uncomment

                    // Get reference to user's account to check eligibility
                    final String id = mAuth.getInstance().getUid();

                    // Get user's education level
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("Users/" + id + "/Demographics");

                    // Attach listener to read the data
                    ref.child("education level").addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String educationLevel = (String)dataSnapshot.getValue();
                            try {
                                String decryptedEducationLevel = EncUtil.decryptMsg(educationLevel, id);

                                switch (decryptedEducationLevel) {
                                    case "Yes, I attend UCSD":
                                        popUpEligible(GAD.this);
                                        break;

                                    default:
                                        popUpIneligible(GAD.this);
                                }

                            } catch(Exception e) {

                            }


                            // THREE CASES FOR USER
                            // User is eligible for Experiment


                            // User is not eligible for Experiment

                            // User may be in need of clinical assistance


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });





                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(GAD.this, LandingPage.class); // TODO: Change to accommodate new LandingPages
                startActivity(intentLoadNewActivity);
                finish();
            }
        });



    }

    // User is eligible for Experiment
    private void popUpEligible(Context context) {
        Toast.makeText(context, "Eligible for Experiment", Toast.LENGTH_LONG);
    }

    // User is not eligible for Experiment
    private void popUpIneligible(Context context) {
        Toast.makeText(context, "Eligible for Experiment", Toast.LENGTH_LONG);
    }

    // User may be in need of clinical assistance
    private void popUpWarning(Context context) {
        Toast.makeText(context, "Eligible for Experiment", Toast.LENGTH_LONG);
    }
}
