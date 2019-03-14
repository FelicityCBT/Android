package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

public class ThoughtSituation extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit;
    private String info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_situation);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mEdit= findViewById(R.id.editText);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info1=mEdit.getText().toString();
                if(info1.equals("")){
                    Toast.makeText(ThoughtSituation.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("ThoughtSituation1",info1);
                    Intent intentLoadNewActivity = new Intent(ThoughtSituation.this, ThoughtMeaning.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });
    }
}
