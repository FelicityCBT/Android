package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;

import java.util.HashMap;

public class CbtIntro extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String, Object> mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbt_intro);
        mInfo= new HashMap<>();

        mNext = findViewById(R.id.next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(CbtIntro.this, CbtChart.class);
                intentLoadNewActivity.putExtra("mInfo",mInfo);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}

