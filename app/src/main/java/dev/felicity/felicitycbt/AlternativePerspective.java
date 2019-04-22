package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.HashMap;

public class AlternativePerspective  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit;
    private SeekBar sb;
    private String info1;
    private int info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative_perspective);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mEdit= findViewById(R.id.edit);
        sb= findViewById(R.id.sb);
        info1="";
        info2=50;

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info1=mEdit.getText().toString();
                if(info1.equals("")){
                    Toast.makeText(AlternativePerspective.this, "Fields cannot be empty",Toast.LENGTH_LONG).show();
                }else {
                    mInfo.put("AlternativePerspective1",info1);
                    mInfo.put("AlternativePerspective2",info2);
                    Intent intentLoadNewActivity = new Intent(AlternativePerspective.this, FeelingSelectionReview.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                info2=progress;
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
