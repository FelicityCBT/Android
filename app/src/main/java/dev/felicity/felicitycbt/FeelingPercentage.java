package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class  FeelingPercentage extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private ArrayList<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_percentage);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        selected= (ArrayList<String>)getIntent().getSerializableExtra("list");

        for(int i=0; i<selected.size();i++){
            LinearLayout myRoot = (LinearLayout) findViewById(R.id.root);
            /*LinearLayout a = new LinearLayout(this);
            a.setOrientation(LinearLayout.HORIZONTAL);*/

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);


            TextView t= new TextView(this);
            t.setText(selected.get(i));
            myRoot.addView(t);

            SeekBar sb= new SeekBar(this);
            sb.setProgress(50);
            myRoot.addView(sb);
            sb.setLayoutParams(lp);

            final int k=i;
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    String info1="Pre"+selected.get(k);
                    mInfo.put(info1,progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            TextView s= new TextView(this);
            s.setText("");
            myRoot.addView(s);

            //myRoot.addView(a);
        }

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(FeelingPercentage.this, Symptoms.class);
                intentLoadNewActivity.putExtra("mInfo",mInfo);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}
