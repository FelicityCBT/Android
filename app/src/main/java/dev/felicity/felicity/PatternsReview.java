package dev.felicity.felicity;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.*;
import com.google.firebase.auth.FirebaseAuth;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class PatternsReview  extends AppCompatActivity {


    private Button mNext;
    private DatabaseReference mDatabase;
    private String mUser;
    private HashMap<String,Object> mInfo;
    private TextView mText;
    private String display;
    private boolean survey;
    //private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patterns_review);

        mNext = findViewById(R.id.finish);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mText= findViewById(R.id.text);
        //btn=findViewById(R.id.res);
        display="";

        ArrayList<String> patt= (ArrayList<String>)mInfo.get("ProblematicPatterns1");
        for(String str:patt){
            display=display+str+"\n";
        }

        mText.setText(display);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime now = LocalDateTime.now();
                String date= now.getMonth().toString()+" "+now.getDayOfMonth()+", "+now.getYear();

                mDatabase.child("Users").child(mUser).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                String key = mDatabase.child("Users").child(mUser).child("Journal").child(date).push().getKey();
                mDatabase.child("Users").child(mUser).child("Journal").child(date).child(key).setValue(now.toString());

                mDatabase.child("Journal").child(key).setValue(mInfo);

                mInfo.clear();

                //for the survey
                survey = false;
                //instantiates a alert dialog
                reviewDialog();
                //secondaryDialog();
                //if(survey) {
                //}
                //calls the method to pop dialog
                //move this to a method if exit is clicked in the alert dialogue
                /*Intent intentLoadNewActivity = new Intent(PatternsReview.this, LandingPage.class);
                intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLoadNewActivity);*/

            }
        });

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog();
            }
        });*/
    }
    public void popDialog(){
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.logo);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).setTitle("Resources").setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        }).
                        setView(image);
        builder.create().show();
    }
    public void reviewDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Would you like to offer feedback for Felicity?");
        //accept button
        alertDialogBuilder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*String reviewURL = "https://goo.gl/forms/KKxvpdiGKAsSBudA3";
                Uri webaddress = Uri.parse(reviewURL);
                Intent gotoWeb = new Intent(Intent.ACTION_VIEW, webaddress);
                if(gotoWeb.resolveActivity(getPackageManager()) != null){
                    Intent webIntent = new Intent(getApplicationContext())
                }*/
                //goes to the URL
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://goo.gl/forms/KKxvpdiGKAsSBudA3"));
                startActivity(viewIntent);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                secondaryDialog();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    public void secondaryDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Would you like to retake the PHQ survey again?");
        //Yes button
        alertDialogBuilder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //page that goes to PHQ survey again
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intentLoadNewActivity = new Intent(PatternsReview.this, LandingPage.class);
                intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLoadNewActivity);
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
