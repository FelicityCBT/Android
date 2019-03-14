package dev.felicity.felicitycbt;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ProblematicPatterns  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private CheckBox[] boxes= new CheckBox[9];
    private ArrayList<String> patt=new ArrayList<>();
    private ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problematic_patterns);

        boxes[0]=findViewById(R.id.checkBox);
        boxes[1]=findViewById(R.id.checkBox2);
        boxes[2]=findViewById(R.id.checkBox3);
        boxes[3]=findViewById(R.id.checkBox4);
        boxes[4]=findViewById(R.id.checkBox5);
        boxes[5]=findViewById(R.id.checkBox6);
        boxes[6]=findViewById(R.id.checkBox7);
        boxes[7]=findViewById(R.id.checkBox8);
        boxes[8]=findViewById(R.id.checkBox9);

        btn=findViewById(R.id.helper);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patt.size()==0){
                    Toast.makeText(ProblematicPatterns.this,"You must select at least one option", Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("ProblematicPatterns1",patt);
                    Intent intentLoadNewActivity = new Intent(ProblematicPatterns.this, FactOrOpinion.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        for(int i=0; i<boxes.length;i++){
            final CheckBox box= boxes[i];
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        patt.add(box.getText().toString());
                    }
                    else{
                        patt.remove(box.getText().toString());
                    }
                }
            });
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
