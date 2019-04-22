package dev.felicity.felicitycbt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;


public class PHQ9 extends AppCompatActivity {

    private RadioGroup[] mRadioGroups= new RadioGroup[17];
    private Button mSubmit;
    private Button mCancel;
    private HashMap<String, Object> mInfo;
    private int score=0;
    private int score2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phq9);

        mInfo= new HashMap<>();

        mSubmit=findViewById(R.id.submit);
        mCancel=findViewById(R.id.cancel);

        mRadioGroups[0]=findViewById(R.id.rg1);
        mRadioGroups[1]=findViewById(R.id.rg2);
        mRadioGroups[2]=findViewById(R.id.rg3);
        mRadioGroups[3]=findViewById(R.id.rg4);
        mRadioGroups[4]=findViewById(R.id.rg5);
        mRadioGroups[5]=findViewById(R.id.rg6);
        mRadioGroups[6]=findViewById(R.id.rg7);
        mRadioGroups[7]=findViewById(R.id.rg8);
        mRadioGroups[8]=findViewById(R.id.rg9);
        mRadioGroups[9]=findViewById(R.id.rg10);
        mRadioGroups[10]=findViewById(R.id.rg11);
        mRadioGroups[11]=findViewById(R.id.rg12);
        mRadioGroups[12]=findViewById(R.id.rg13);
        mRadioGroups[13]=findViewById(R.id.rg14);
        mRadioGroups[14]=findViewById(R.id.rg15);
        mRadioGroups[15]=findViewById(R.id.rg16);
        mRadioGroups[16]=findViewById(R.id.rg17);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                boolean error= false;

                for(int i=0; i<9;i++){
                    RadioGroup group= mRadioGroups[i];
                    if (group.getCheckedRadioButtonId() == -1)
                    {
                        Toast.makeText(PHQ9.this,"All questions must be answered",Toast.LENGTH_LONG).show();
                        error=true;
                        break;
                    }
                    int q_score=group.indexOfChild(findViewById(group.getCheckedRadioButtonId()));
                    score=score+q_score;
                    String key= "PHQ9 question "+i;
                    mInfo.put(key, q_score);
                }

                for(int i=9; i<16;i++){
                    RadioGroup group= mRadioGroups[i];
                    if (group.getCheckedRadioButtonId() == -1)
                    {
                        Toast.makeText(PHQ9.this,"All questions must be answered",Toast.LENGTH_LONG).show();
                        error=true;
                        break;
                    }
                    int q_score=group.indexOfChild(findViewById(group.getCheckedRadioButtonId()));
                    score2=score2+q_score;
                    String key= "GAD7 question "+i;
                    mInfo.put(key, q_score);
                }

                if(!error){
                    //for db
                    //mInfo.put("score",score); //TODO: Ask Karen if we should store this info in the database

                    //encryption/decryption test
                    /*String uid= FirebaseAuth.getInstance().getUid();
                    try {
                        String ct= EncUtil.encryptMsg(""+score, uid);
                        Toast.makeText(PHQ9.this,ct,Toast.LENGTH_LONG).show();
                        String uct=EncUtil.decryptMsg(ct, uid);
                        Toast.makeText(PHQ9.this,uct,Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e){
                        Toast.makeText(PHQ9.this,e.toString(),Toast.LENGTH_LONG).show();
                    }*/
                    int lq_score=mRadioGroups[16].indexOfChild(findViewById(mRadioGroups[16].getCheckedRadioButtonId()));
                    if(score<=14 && score2<=10 && lq_score<=2) {
                        popDialog();
                    }
                    else{
                        popDialog1();
                    }
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(PHQ9.this, LandingPage.class);
                startActivity(intentLoadNewActivity);
                finish();
            }
        });
    }

    public void popDialog(){
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.phq9_table);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).setTitle("PHQ-9 Score").setMessage("Your Score: "+(score+score2)+"\nUse this app accordingly").setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intentLoadNewActivity = new Intent(PHQ9.this, LandingPage.class);
                                startActivity(intentLoadNewActivity);
                                finish();
                            }
                        }).setView(image);
        builder.create().show();
    }

    public void popDialog1(){
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.phq9_table);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).setMessage(R.string.screen_risk).setPositiveButton("Continue",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intentLoadNewActivity = new Intent(PHQ9.this, LandingPage.class);
                                startActivity(intentLoadNewActivity);
                                finish();
                            }
                        }).setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                                System.exit(0);
                            }
                        });
        builder.create().show();
    }
}