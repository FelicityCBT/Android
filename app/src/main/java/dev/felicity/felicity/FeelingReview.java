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

public class FeelingReview  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private ImageButton [] buttons=new ImageButton[7];
    private String info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_review);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        buttons[0]=findViewById(R.id.Loved);
        buttons[1]=findViewById(R.id.Happy);
        buttons[2]=findViewById(R.id.Nervous);
        buttons[3]=findViewById(R.id.Angry);
        buttons[4]=findViewById(R.id.Sad);
        buttons[5]=findViewById(R.id.Tired);
        buttons[6]=findViewById(R.id.Okay);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info2==null){
                    Toast.makeText(FeelingReview.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else{
                    // TODO: Change to ResponseOverview Page
                    // Intent intentLoadNewActivity = new Intent(FeelingReview.this, ResponseOverview.class);
                    Intent intentLoadNewActivity = new Intent(FeelingReview.this, PatternsReview.class);
                    mInfo.put("FeelingReview1",info2);
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

                        case R.id.Okay:
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
