package dev.felicity.felicity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.HashMap;

public class BlankSession extends AppCompatActivity {

    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit;
    private String info1;
    private DatabaseReference mDatabase;
    private String mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_session);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String, Object>)getIntent().getSerializableExtra("mInfo");
        mEdit = findViewById(R.id.editBlank);
        info1 = "";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info1 = mEdit.getText().toString();

                if(info1.equals("")) {
                    Toast.makeText(BlankSession.this, "Field cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    mInfo.put("BlankSession1", info1);

//                    Toast.makeText(BlankSession.this, info1, Toast.LENGTH_LONG).show();

                    // Submit data to database
                    LocalDateTime now = LocalDateTime.now();
                    String date= now.getMonth().toString()+" "+now.getDayOfMonth()+", "+now.getYear();

                    mDatabase.child("Users").child(mUser).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    String key = mDatabase.child("Users").child(mUser).child("Journal").child(date).push().getKey();
                    mDatabase.child("Users").child(mUser).child("Journal").child(date).child(key).setValue(now.toString());

                    mDatabase.child("Journal").child(key).setValue(mInfo);

                    mInfo.clear();

                    // Go back to Landing Page
                    Intent intentLoadNewActivity = new Intent(BlankSession.this, LandingPage.class);
                    startActivity(intentLoadNewActivity);

                }
            }
        });
    }
}
