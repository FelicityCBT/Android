package dev.felicity.felicitycbt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.HashMap;

public class Temp extends AppCompatActivity {

    private HashMap<String, Object> mInfo;
    private DatabaseReference mDatabase;
    private String mUser;

    // Variables to determine experiment path
    private boolean isUCSD;
    private boolean isOfAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        // By default, these variables are false
        isUCSD = false;
        isOfAge = false;

        // Store ResearchDemographics
        mDatabase.child("Users").child(mUser).child("ResearchDemographics").setValue(mInfo);


        // Calculate questionnaire score of user
        Integer phqScore = (Integer)getIntent().getSerializableExtra("PHQScore");
        Integer gadScore = (Integer)getIntent().getSerializableExtra("GADScore");
        Integer totalScore = phqScore + gadScore;
        Toast.makeText(Temp.this, ""+totalScore, Toast.LENGTH_LONG).show();


        // Get if participant is a UCSD student
        DatabaseReference UCSDRef = mDatabase.child("Users").child(mUser).child("Demographics").child("education level");
        UCSDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String strUCSD = dataSnapshot.getValue(String.class);
                try {
                    strUCSD = EncUtil.decryptMsg(strUCSD, mUser);

                    if(strUCSD.equals("Yes, I attend UCSD")) {
                        isUCSD = true;
//                        Toast.makeText(Temp.this, strUCSD, Toast.LENGTH_LONG).show();
                    }

                } catch(Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Get age of participant. Only 18-24 eligible
        DatabaseReference ageRef = mDatabase.child("Users").child(mUser).child("Demographics").child("age");
        ageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ageStr = dataSnapshot.getValue(String.class);
                try {
                    ageStr = EncUtil.decryptMsg(ageStr, mUser);
                    int age = Integer.parseInt(ageStr);

                    if(age >= 18 && age <= 24) {
                        isOfAge = true;
//                        Toast.makeText(Temp.this, "Is of age", Toast.LENGTH_LONG).show();
                    }

                } catch(Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        // Show pop-up showing eligibility of user






        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



//    public void showEligiblePopup() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setTitle("You are eligible for the study.");
//        alertDialogBuilder.setMessage(R.string.screeningTools);
//        alertDialogBuilder.setPositiveButton("Continue",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent intentLoadNewActivity = new Intent(Demographics.this, PHQ9.class);
//                        intentLoadNewActivity.putExtra("mInfo", mInfo);
//                        startActivity(intentLoadNewActivity);
//                    }
//                });
//        alertDialogBuilder.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
//                        com.facebook.login.LoginManager.getInstance().logOut();
//                        Intent intentLoadNewActivity = new Intent(Demographics.this, Login.class);
//                        intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intentLoadNewActivity);
//                        finish();
//                    }
//                });
//        alertDialogBuilder.create().show();
//    }

}
