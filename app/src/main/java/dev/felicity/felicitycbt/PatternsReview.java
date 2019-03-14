package dev.felicity.felicitycbt;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
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

                Intent intentLoadNewActivity = new Intent(PatternsReview.this, LandingPage.class);
                intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLoadNewActivity);
                finish();
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
}
