package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class FactOrOpinion  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit;
    private RadioGroup rg;
    private String info1;
    private String info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_or_opinion);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mEdit=findViewById(R.id.edit);
        rg= findViewById(R.id.rg);
        info1="";
        info2="";

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info1=mEdit.getText().toString();
                if(info1.equals("") || info2.equals("")){
                    Toast.makeText(FactOrOpinion.this,"Fields can not be empty", Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("FactOrOpinion1",info1);
                    mInfo.put("FactOrOpinion2",info2);
                    Intent intentLoadNewActivity = new Intent(FactOrOpinion.this, AlternativeInterpretations.class);
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
                        info2="Fact";
                        break;
                    case 1: // secondbutton
                        info2="Opinion";
                        break;
                }
            }
        });
    }
}
