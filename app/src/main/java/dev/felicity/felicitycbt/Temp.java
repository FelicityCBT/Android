package dev.felicity.felicitycbt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Temp extends AppCompatActivity {

    private HashMap<String, Object> mInfo;
    private DatabaseReference mDatabase;
    private String mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        String key = mDatabase.child("Users").child(mUser).child("ResearchDemographics").push().getKey();
        mDatabase.child("Users").child(mUser).child("ResearchDemographics").child(key);
        mDatabase.child("Users").child(mUser).child("ResearchDemographics").child(key).setValue(mInfo);

        // TODO: Try to retrieve information back from the Database

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
