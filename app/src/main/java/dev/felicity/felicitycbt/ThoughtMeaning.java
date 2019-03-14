package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

public class ThoughtMeaning extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit1;
    private EditText mEdit2;
    private String info1;
    private String info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_meaning);

        mNext = findViewById(R.id.next);
        mEdit1=findViewById(R.id.editText1);
        mEdit2=findViewById(R.id.editText2);

        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info1=mEdit1.getText().toString();
                info2=mEdit2.getText().toString();

                if(info1.equals("") || info2.equals("")){
                    Toast.makeText(ThoughtMeaning.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("ThoughtMeaning1",info1);
                    mInfo.put("ThoughtMeaning2",info2);
                    Intent intentLoadNewActivity = new Intent(ThoughtMeaning.this, FeelingSelection.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });
    }
}
