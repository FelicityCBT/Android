package dev.felicity.felicity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class CopingMechanisms  extends AppCompatActivity {


    private ImageButton btn;
    private ImageButton mNext;
    private HashMap<String, Object> mInfo;
    private EditText mEdit1;
    private EditText mEdit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coping_mechanisms);

        btn = findViewById(R.id.helper);
        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String, Object>) getIntent().getSerializableExtra("mInfo");
        mEdit1 = findViewById(R.id.editText1);
        mEdit2 = findViewById(R.id.editText2);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1 = mEdit1.getText().toString();
                String info2 = mEdit2.getText().toString();
                if (info1.equals("") || info2.equals("")) {
                    Toast.makeText(CopingMechanisms.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    mInfo.put("CopingMechanisms1", info1);
                    mInfo.put("CopingMechanisms2", info2);
                    Intent intentLoadNewActivity = new Intent(CopingMechanisms.this, AvoidanceAssessment.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
            popDialog();

            }
        });
    }
    public void popDialog(){
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.patterns);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).setTitle("Problematic Patterns").setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        }).
                        setView(image);
        builder.create().show();
    }
}
