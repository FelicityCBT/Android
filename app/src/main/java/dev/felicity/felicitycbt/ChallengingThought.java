package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ChallengingThought  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private TextView mText;
    private RadioGroup rg;
    private String info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenging_thought);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        rg=findViewById(R.id.rg);
        mText=findViewById(R.id.text);
        info1="";


        mText.setText((String)mInfo.get("ThoughtSituation1"));

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info1.equals("")){
                    Toast.makeText(ChallengingThought.this,"Select either factual or exaggerated",Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("ChallengingThought1",info1);
                    Intent intentLoadNewActivity = new Intent(ChallengingThought.this, ThoughtExclusion.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
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
                        info1="Factual";
                        break;
                    case 1: // secondbutton
                        info1="Exaggerated";
                        break;
                }
            }
        });
    }
}
