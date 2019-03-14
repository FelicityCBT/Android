package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ThoughtExclusion  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private TextView mText;
    private EditText mEdit;
    private RadioGroup rg;
    private String info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_exclusion);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mText=findViewById(R.id.text);
        mEdit=findViewById(R.id.edit);
        rg= findViewById(R.id.rg);
        info1="";

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info1.equals("no")) {
                    mInfo.put("ThoughtExclusion1","n/a");
                    Intent intentLoadNewActivity = new Intent(ThoughtExclusion.this, ProblematicPatterns.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
                else {
                    info1 = mEdit.getText().toString();
                    if (info1.equals("")) {
                        Toast.makeText(ThoughtExclusion.this, "All fields must be answered", Toast.LENGTH_LONG).show();
                    } else {
                        mInfo.put("ThoughtExclusion1", info1);
                        Intent intentLoadNewActivity = new Intent(ThoughtExclusion.this, ProblematicPatterns.class);
                        intentLoadNewActivity.putExtra("mInfo", mInfo);
                        startActivity(intentLoadNewActivity);
                    }
                }
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0: // first button
                        mText.setVisibility(View.VISIBLE);
                        mEdit.setVisibility(View.VISIBLE);
                        break;
                    case 1: // secondbutton
                        info1="no";
                        mText.setVisibility(View.INVISIBLE);
                        mEdit.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }
}
