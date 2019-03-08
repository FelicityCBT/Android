package dev.felicity.felicity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.HashMap;

public class GratuityLog  extends AppCompatActivity {


    private Button mContinue;
    private Button mExit;
    private HashMap<String,Object> mInfo, mDemographics;
    private String info1;
    private TextView mText;
    private EditText mEdit;
    private String mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gratuity_log);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        mUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        // mDemographics = (HashMap<String,Object>)getIntent().getSerializableExtra("mDemographics");
        mEdit=findViewById(R.id.textbox);
        mExit=findViewById(R.id.exit);
        mUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info1 = mEdit.getText().toString();
                if (info1.equals("")) {
                    Toast.makeText(GratuityLog.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                }
                else {

                    mInfo.put("GratuityLog1", info1);
                    LocalDateTime now = LocalDateTime.now();
                    String date= now.getMonth().toString()+" "+now.getDayOfMonth()+", "+now.getYear();


                    mDatabase.child("Users").child(mUser).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    String key = mDatabase.child("Users").child(mUser).child("Journal").child(date).push().getKey();
                    mDatabase.child("Users").child(mUser).child("Journal").child(date).child(key).setValue(now.toString());

                    try {
                        mInfo = EncUtil.encMap(mInfo, FirebaseAuth.getInstance().getUid());
                    }
                    catch(Exception e){
                        /*TODO: nothing for now*/
                    }
                    mDatabase.child("Journal").child(key).setValue(mInfo);

                    mInfo.clear();
                    //mDemographics.clear();
                    Intent intentLoadNewActivity = new Intent(GratuityLog.this, LandingPage.class);
                    intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentLoadNewActivity);
                    finish();
                }
            }
        });
    }
}

