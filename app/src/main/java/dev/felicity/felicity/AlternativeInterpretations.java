package dev.felicity.felicity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class AlternativeInterpretations  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit1;
    private EditText mEdit2;
    private ArrayList<String> interpretations = new ArrayList<String>(); // Holds user responses to "What are the positives..."
    private LinearLayout mAddedTextLayout;
    private Button mAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative_interpretations);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mEdit1=findViewById(R.id.editText1);
        mEdit2=findViewById(R.id.editText2);
        mAddedTextLayout=findViewById(R.id.addedTextLayout);
        mAddBtn = findViewById(R.id.addBtn);

        // Dynamically Add Text
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEdit2.getText().toString().equals("")) {
                    mAddedTextLayout.addView(createNewTextView(mEdit2.getText().toString()));
                    interpretations.add(mEdit2.getText().toString());
                    mEdit2.setText("");
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1=mEdit1.getText().toString();

                if(info1.equals("") || interpretations.size() == 0){
                    Toast.makeText(AlternativeInterpretations.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }else {
                    mInfo.put("AlternativeInterpretations1",info1);
                    mInfo.put("AlternativeInterpretations2",interpretations);
                    Intent intentLoadNewActivity = new Intent(AlternativeInterpretations.this, ThoughtComparison.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });
    }

    // Creates and returns a new TextView to be added to the screen dynamically
    private TextView createNewTextView(String text) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        return textView;
    }
}
