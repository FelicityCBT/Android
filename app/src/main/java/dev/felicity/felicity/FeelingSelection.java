package dev.felicity.felicity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FeelingSelection extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private Button[] buttons=new Button[14];//[9];
    private boolean [] clicked=new boolean[14];//[9];
    private ArrayList<String> selected= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_selection);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        clicked= new boolean[14];//[9];

        for(int i=0;i<clicked.length;i++){
            clicked[i]=false;
        }

        buttons[0]=findViewById(R.id.button2);
        buttons[1]=findViewById(R.id.button3);
        buttons[2]=findViewById(R.id.button4);
        buttons[3]=findViewById(R.id.button5);
        buttons[4]=findViewById(R.id.button6);
        buttons[5]=findViewById(R.id.button7);
        buttons[6]=findViewById(R.id.button8);
        buttons[7]=findViewById(R.id.button9);
        buttons[8]=findViewById(R.id.button10);
        buttons[9]=findViewById(R.id.button11);
        buttons[10]=findViewById(R.id.button12);
        buttons[11]=findViewById(R.id.button13);
        buttons[12]=findViewById(R.id.button14);
        buttons[13]=findViewById(R.id.button15);

        mInfo.put("PreAnger",0);
        mInfo.put("PreWorthlessness",0);
        mInfo.put("PreLethargy",0);
        mInfo.put("PreEmptiness",0);
        mInfo.put("PreHopelessness",0);
        mInfo.put("PreGuilt",0);
        mInfo.put("PreIrritation",0);
        mInfo.put("PreShame",0);
        mInfo.put("PreFrustration",0);
        mInfo.put("PreSensitive",0);
        mInfo.put("PreFear",0);
        mInfo.put("PreNervousness",0);
        mInfo.put("PreVulnerable",0);
        mInfo.put("PrePanic",0);



        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected.size()==0){
                    Toast.makeText(FeelingSelection.this,"Select at least one emotion",Toast.LENGTH_LONG).show();
                }else{
                    Intent intentLoadNewActivity = new Intent(FeelingSelection.this, FeelingPercentage.class);
                    intentLoadNewActivity.putExtra("mInfo",mInfo);
                    intentLoadNewActivity.putExtra("list",selected);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        for (int i=0;i<buttons.length;i++) {
            final int j=i;
            final Button button=buttons[i];
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String info1="Pre"+button.getText().toString();
                    if (clicked[j]) {
                        button.setBackgroundResource(R.drawable.button_back);
                        clicked[j] = false;
                        selected.remove(button.getText().toString());
                        mInfo.put(info1,0);
                    } else {
                        button.setBackgroundResource(R.drawable.button_clicked);
                        clicked[j] = true;
                        selected.add(button.getText().toString());
                        mInfo.put(info1,50);
                    }
                }

            });
        }
    }
}
