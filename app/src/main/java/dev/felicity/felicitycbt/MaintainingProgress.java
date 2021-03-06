package dev.felicity.felicitycbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MaintainingProgress  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;

    private EditText mEdit1;

    private EditText mEditHelped;
    private LinearLayout mLayoutHelped;
    private Button mAddBtnHelped;
    private ArrayList<String> helped = new ArrayList<>();


    private EditText mEditNotHelped;
    private LinearLayout mLayoutNotHelped;
    private Button mAddBtnNotHelped;
    private ArrayList<String> notHelped = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintaining_progress);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mEdit1=findViewById(R.id.editText1);

        mEditHelped=findViewById(R.id.editTextHelped);
        mLayoutHelped=findViewById(R.id.addedTextLayoutHelped);
        mAddBtnHelped=findViewById(R.id.addBtnHelped);

        // Dynamically add text for What Helped User
        mAddBtnHelped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditHelped.getText().toString().equals("")) {
                    TextView editHelped = createNewTextView(mEditHelped.getText().toString());
                    editHelped.setPadding(5, 5, 5, 5);
                    editHelped.setTextAppearance(R.style.Headers);
                    mLayoutHelped.setGravity(Gravity.CENTER);
                    mLayoutHelped.addView(editHelped);
                    helped.add(mEditHelped.getText().toString());
                    mEditHelped.setText("");
                }
            }
        });

        mEditNotHelped=findViewById(R.id.editTextNotHelped);
        mLayoutNotHelped=findViewById(R.id.addedTextLayoutNotHelped);
        mAddBtnNotHelped=findViewById(R.id.addBtnNotHelped);

        // Dynamically add text for What Helped User
        mAddBtnNotHelped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditNotHelped.getText().toString().equals("")) {
                    TextView editNotHelped = createNewTextView(mEditNotHelped.getText().toString());
                    editNotHelped.setPadding(5,5,5,5);
                    editNotHelped.setTextAppearance(R.style.Headers);
                    mLayoutNotHelped.setGravity(Gravity.CENTER);
                    mLayoutNotHelped.addView(editNotHelped);
                    notHelped.add(mEditNotHelped.getText().toString());
                    mEditNotHelped.setText("");
                }
            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1=mEdit1.getText().toString();
                String info2 = mEditHelped.getText().toString();
                String info3 = mEditNotHelped.getText().toString();

                if(info1.equals("") || (info2.equals("") && helped.size()==0) || (info3.equals("") && notHelped.size() == 0)){
                    Toast.makeText(MaintainingProgress.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                } else if(!info1.equals("") && !info2.equals("") && !info3.equals("")) {
                    helped.add(info2);
                    notHelped.add(info3);
                    mInfo.put("MaintainingProgress1", info1);
                    mInfo.put("MaintainingProgress2", helped);
                    mInfo.put("MaintainingProgress3", notHelped);
                    Intent intentLoadNewActivity = new Intent(MaintainingProgress.this, FeelingReview.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
                else {
                    if(!info2.equals("")) helped.add(info2);
                    if(!info3.equals("")) notHelped.add(info3);
                    mInfo.put("MaintainingProgress1",info1);
                    mInfo.put("MaintainingProgress2", helped);
                    mInfo.put("MaintainingProgress3", notHelped);
                    Intent intentLoadNewActivity = new Intent(MaintainingProgress.this, FeelingReview.class);
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
