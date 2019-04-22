package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;

public class Symptoms  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private CheckBox[] boxes= new CheckBox[9];
    private EditText mEdit;
    private ArrayList<String> symp=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        boxes[0]=findViewById(R.id.checkBox);
        boxes[1]=findViewById(R.id.checkBox2);
        boxes[2]=findViewById(R.id.checkBox3);
        boxes[3]=findViewById(R.id.checkBox4);
        boxes[4]=findViewById(R.id.checkBox5);
        boxes[5]=findViewById(R.id.checkBox6);
        boxes[6]=findViewById(R.id.checkBox7);
        boxes[7]=findViewById(R.id.checkBox8);
        boxes[8]=findViewById(R.id.checkBox9);
        mEdit = findViewById(R.id.editText);



        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otherSymp = mEdit.getText().toString();
                if(!otherSymp.equals("")) {
                    mInfo.put("Other Symptom", otherSymp);
                }
                mInfo.put("Symptoms",symp);
                Intent intentLoadNewActivity = new Intent(Symptoms.this, AffectInfluence.class);
                intentLoadNewActivity.putExtra("mInfo",mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

        for(int i=0; i<boxes.length;i++){
            final CheckBox box= boxes[i];
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        symp.add(box.getText().toString());
                    }
                    else{
                        symp.remove(box.getText().toString());
                    }
                }
            });
        }
    }
}
