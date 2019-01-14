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

public class FeelingSelectionReview extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private Button[] buttons=new Button[14];
    private boolean [] clicked=new boolean[14];
    private ArrayList<String> selected= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_selection_review);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        clicked= new boolean[14];

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

        mInfo.put("PostAnger",0);
        mInfo.put("PostWorthlessness",0);
        mInfo.put("PostLethargy",0);
        mInfo.put("PostEmptiness",0);
        mInfo.put("PostHopelessness",0);
        mInfo.put("PostGuilt",0);
        mInfo.put("PostIrritation",0);
        mInfo.put("PostShame",0);
        mInfo.put("PostFrustration",0);
        mInfo.put("PostSensitive",0);
        mInfo.put("PostFear",0);
        mInfo.put("PostNervousness",0);
        mInfo.put("PostVulnerable",0);
        mInfo.put("PostPanic",0);



        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected.size()==0){
                    Intent intentLoadNewActivity = new Intent(FeelingSelectionReview.this, MaintainingProgress.class);
                    intentLoadNewActivity.putExtra("mInfo",mInfo);
                    intentLoadNewActivity.putExtra("list",selected);
                    startActivity(intentLoadNewActivity);
                }else{
                    Intent intentLoadNewActivity = new Intent(FeelingSelectionReview.this, FeelingPercentageReview.class);
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

                    String info1="Post"+button.getText().toString();
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
