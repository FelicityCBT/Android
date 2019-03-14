package dev.felicity.felicity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ResponseOverview extends AppCompatActivity {

    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private DatabaseReference mDatabase;
    private String mUser;
    private LinearLayout mEmotionsStartLayout;
    private LinearLayout mEmotionsEndLayout;
    private String prosDisplay;
    private TextView mText3;
    private String consDisplay;
    private TextView mText4;
    private String problematicPatternsDisplay;
    private TextView mText5;
    private boolean survey;

    public ResponseOverview() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_overview);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        /***** Set display for before-emotions *****/
        String emotionBefore = (String) mInfo.get("SituationDescription2");
        mEmotionsStartLayout = findViewById(R.id.emotionsStart);
        int emotionSrc = getEmotionImgSrc(emotionBefore);
        ImageView emotionImg = new ImageView(ResponseOverview.this);
        emotionImg.setBackgroundResource(emotionSrc);
        mEmotionsStartLayout.setGravity(Gravity.CENTER);
        mEmotionsStartLayout.addView(emotionImg);

        /***** Set display for after-emotions *****/
        String emotionAfter = (String) mInfo.get("FeelingReview1");
        mEmotionsEndLayout = findViewById(R.id.emotionsAfter);
        emotionSrc = getEmotionImgSrc(emotionAfter);
        emotionImg = new ImageView(ResponseOverview.this);
        emotionImg.setBackgroundResource(emotionSrc);
        mEmotionsEndLayout.setGravity(Gravity.CENTER);
        mEmotionsEndLayout.addView(emotionImg);

        /***** Set display for pros *****/
        prosDisplay = "";
        ArrayList<String> pros = (ArrayList<String>)mInfo.get("AvoidanceAssessment2");
        for(String str : pros) {
            prosDisplay = prosDisplay + "• " + str + "\n";
        }
        mText3 = findViewById(R.id.pros);
        mText3.setText(prosDisplay);
//        mText3.setText("• " + prosDisplay);

        /***** Set display for cons *****/
        consDisplay = "";
        ArrayList<String> cons = (ArrayList<String>)mInfo.get("AvoidanceAssessment3");
        for(String str : cons) {
            consDisplay = consDisplay + "• " + str + "\n";
        }
        mText4 = findViewById(R.id.cons);
        mText4.setText(consDisplay);

        /***** Set display for problematic patterns *****/
        problematicPatternsDisplay="";
        ArrayList<String> problematicPatt= (ArrayList<String>)mInfo.get("ProblematicPatterns1");
        for(String str : problematicPatt){
            problematicPatternsDisplay = problematicPatternsDisplay + "• " + str+"\n";
        }
        mText5 = findViewById(R.id.problematicPatterns);
        mText5.setText(problematicPatternsDisplay);

        /***** Next button *****/
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime now = LocalDateTime.now();
                String date= now.getMonth().toString()+" "+now.getDayOfMonth()+", "+now.getYear();

                mDatabase.child("Users").child(mUser).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                String key = mDatabase.child("Users").child(mUser).child("Journal").child(date).push().getKey();
                mDatabase.child("Users").child(mUser).child("Journal").child(date).child(key).setValue(now.toString());

                try {
                    mInfo = EncUtil.encMap(mInfo, FirebaseAuth.getInstance().getUid());
                }
                catch(Exception e){
                    //TODO: nothing for now
                }
                mDatabase.child("Journal").child(key).setValue(mInfo);

                mInfo.clear();

                survey = false;

                //instantiates a alert dialog
                reviewDialog();

            }
        });

    }

    /***** Function handling user wanting to write feedback *****/
    public void reviewDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Would you like to offer feedback for Felicity?");
        //accept button
        alertDialogBuilder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //goes to the URL
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://goo.gl/forms/KKxvpdiGKAsSBudA3"));
                startActivity(viewIntent);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intentLoadNewActivity = new Intent(ResponseOverview.this, LandingPage.class);
                intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLoadNewActivity);
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    /***** Function handles which emotion to display *****/
    public int getEmotionImgSrc(String emotion) {
        switch(emotion) {
            case "Loved":
                return R.drawable.loved1;
            case "Happy":
                return R.drawable.happy1;
            case "Sad":
                return R.drawable.sad1;
            case "Tired":
                return R.drawable.tired1;
            case "Nervous":
                return R.drawable.nervous1;
            case "Angry":
                return R.drawable.angry;
            case "Okay":
                return R.drawable.okay1;
            default:
                break;
        }
        return -1;
    }

}
