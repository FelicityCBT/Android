package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;

public class ThoughtComparison  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private TextView mText;
    private SeekBar sb;
    private int info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_comparison);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mText=findViewById(R.id.text);
        sb=findViewById(R.id.sb);
        info1=50;

        mText.setText((String)mInfo.get("ThoughtSituation1"));

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfo.put("ThoughtComparison1",info1);
                Intent intentLoadNewActivity = new Intent(ThoughtComparison.this, AlternativePerspective.class);
                intentLoadNewActivity.putExtra("mInfo",mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

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
    }
}
