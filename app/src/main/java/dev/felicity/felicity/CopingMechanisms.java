package dev.felicity.felicity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class CopingMechanisms  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit1;
    private EditText mEdit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coping_mechanisms);
        // These pertain to coping mechanisms USED
        boxes[0] = findViewById(R.id.checkBox);
        boxes[1] = findViewById(R.id.checkBox2);
        boxes[2] = findViewById(R.id.checkBox3);
        boxes[3] = findViewById(R.id.checkBox4);
        boxes[4] = findViewById(R.id.checkBox5);
        boxes[5] = findViewById(R.id.checkBox6);
        boxes[6] = findViewById(R.id.checkBox7);
        boxes[7] = findViewById(R.id.checkBox8);
        boxes[8] = findViewById(R.id.checkBox9);
        boxes[9] = findViewById(R.id.checkBox10);
        boxes[10] = findViewById(R.id.checkBox11);

        // These pertain to coping mechanisms NOT USED
        boxes2[0] = findViewById(R.id.checkBox12);
        boxes2[1] = findViewById(R.id.checkBox13);
        boxes2[2] = findViewById(R.id.checkBox14);
        boxes2[3] = findViewById(R.id.checkBox15);
        boxes2[4] = findViewById(R.id.checkBox16);
        boxes2[5] = findViewById(R.id.checkBox17);
        boxes2[6] = findViewById(R.id.checkBox18);
        boxes2[7] = findViewById(R.id.checkBox19);
        boxes2[8] = findViewById(R.id.checkBox20);
        boxes2[9] = findViewById(R.id.checkBox21);
        boxes2[10] = findViewById(R.id.checkBox22);



        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mEdit1=findViewById(R.id.editText1);
        mEdit2=findViewById(R.id.editText2);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1=mEdit1.getText().toString();
                String info2= mEdit2.getText().toString();
                if(info1.equals("") || info2.equals("")){
                    Toast.makeText(CopingMechanisms.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("CopingMechanisms1", info1);
                    mInfo.put("CopingMechanisms2", info2);
                    Intent intentLoadNewActivity = new Intent(CopingMechanisms.this, AvoidanceAssessment.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });
    }
}
