package dev.felicity.felicity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.HashMap;

public class SituationDescription extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private SeekBar mBar;
    private ImageButton [] buttons=new ImageButton[7];
    private int info1;
    private String info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_description);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        buttons[0]=findViewById(R.id.Loved);
        buttons[1]=findViewById(R.id.Happy);
        buttons[2]=findViewById(R.id.Nervous);
        buttons[3]=findViewById(R.id.Angry);
        buttons[4]=findViewById(R.id.Sad);
        buttons[5]=findViewById(R.id.Tired);
        buttons[6]=findViewById(R.id.Okay1);
        mBar=findViewById(R.id.seekBar2);

        info1 = 50;

        mBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               info1=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info2==null){
                    Toast.makeText(SituationDescription.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intentLoadNewActivity = new Intent(SituationDescription.this, ThoughtDescription.class);
                    mInfo.put("SituationDescription1",info1);
                    mInfo.put("SituationDescription2",info2);
                    intentLoadNewActivity.putExtra("mInfo",mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        for (final ImageButton button:buttons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (ImageButton b:buttons){
                        b.setBackgroundResource(R.drawable.button_back);
                    }
                    button.setBackgroundResource(R.drawable.button_clicked);
                    switch (view.getId()) {

                        case R.id.Loved:
                            info2="Loved";
                            break;

                        case R.id.Happy:
                            info2="Happy";
                            break;

                        case R.id.Sad:
                            info2="Sad";
                            break;

                        case R.id.Tired:
                            info2="Tired";
                            break;

                        case R.id.Nervous:
                            info2="Nervous";
                            break;

                        case R.id.Angry:
                            info2="Angry";
                            break;

                        case R.id.Okay1:
                            info2="Okay";
                            break;

                        default:
                            break;
                    }
                }
            });
        }
    }
}
