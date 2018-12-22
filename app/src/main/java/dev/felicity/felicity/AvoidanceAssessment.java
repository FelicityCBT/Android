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

public class AvoidanceAssessment  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;

    private EditText mEditAvoid;
    private Button mAddBtnAvoid;
    private ArrayList<String> avoided = new ArrayList<>();
    private LinearLayout mLayoutAvoid;

    private EditText mEditPros;
    private Button mAddBtnPros;
    private ArrayList<String> pros= new ArrayList<>();
    private LinearLayout mLayoutPros;

    private EditText mEditCons;
    private Button mAddBtnCons;
    private ArrayList<String> cons= new ArrayList<>();
    private LinearLayout mLayoutCons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avoidance_assessment);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mEditAvoid=findViewById(R.id.editTextAvoid);
        mAddBtnAvoid = findViewById(R.id.addBtnAvoid);
        mLayoutAvoid = findViewById(R.id.addedTextLayoutAvoid);

        mEditPros=findViewById(R.id.editTextPros);
        mAddBtnPros = findViewById(R.id.addBtnPros);
        mLayoutPros = findViewById(R.id.addedTextLayoutPros);

        mEditCons=findViewById(R.id.editTextCons);
        mAddBtnCons = findViewById(R.id.addBtnCons);
        mLayoutCons = findViewById(R.id.addedTextLayoutCons);

        // Dynamically Add Text for Avoid
        mAddBtnAvoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditAvoid.getText().toString().equals("")) {
                    mLayoutAvoid.addView(createNewTextView(mEditAvoid.getText().toString()));
                    avoided.add(mEditAvoid.getText().toString());
                    mEditAvoid.setText("");
                }
            }
        });

        // Dynamically Add Text for Pros
        mAddBtnPros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditPros.getText().toString().equals("")) {
                    mLayoutPros.addView(createNewTextView(mEditPros.getText().toString()));
                    pros.add(mEditPros.getText().toString());
                    mEditPros.setText("");
                }
            }
        });

        // Dynamically Add Text for Cons
        mAddBtnCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditCons.getText().toString().equals("")) {
                    mLayoutCons.addView(createNewTextView(mEditCons.getText().toString()));
                    cons.add(mEditCons.getText().toString());
                    mEditCons.setText("");
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(avoided.size() == 0 || pros.size() == 0 || cons.size() == 0){
                    Toast.makeText(AvoidanceAssessment.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("AvoidanceAssessmentAvoided",avoided);
                    mInfo.put("AvoidanceAssessmentPros",pros);
                    mInfo.put("AvoidanceAssessmentCons",cons);
                    Intent intentLoadNewActivity = new Intent(AvoidanceAssessment.this, AlternativeOptions.class);
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