package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

public class AffectInfluence  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit1;
    private EditText mEdit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affect_influence);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mEdit1=findViewById(R.id.editText1);
        mEdit2=findViewById(R.id.editText2);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1 = mEdit1.getText().toString();
                String info2 = mEdit2.getText().toString();
                if (info1.equals("") || info2.equals("")) {
                    Toast.makeText(AffectInfluence.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    mInfo.put("AffectInfluence1", info1);
                    mInfo.put("AffectInfluence2", info2);
                    Intent intentLoadNewActivity = new Intent(AffectInfluence.this, CopingMechanisms.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });
    }
}
