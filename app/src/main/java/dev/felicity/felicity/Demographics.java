package dev.felicity.felicity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Demographics extends AppCompatActivity {


    private ImageButton mNext;
    private RadioGroup mHealth, mKind, mYear, mEducation, mEthnicity, mGender, mAge;
    private RadioButton mHave, mCurrent, mNever;
    private RadioButton mUCSD, mCollege, mN, mp4;
    private RadioButton mYes, mNo, m1, m2, m3, m4, m5, mp2, mNA;
    private RadioButton mWhite, mBlack, mAsian, mNative, mOther, mp3, mHispanic;
    private RadioButton mMale, mFemale, mNew, mp, mp5;
    private TextView mifYes;
    private HashMap<String, Object> mDemographics;
    private ArrayList<String> demo = new ArrayList<>();
    private CheckBox[] boxes = new CheckBox[4];
    private EditText mUserage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics);

        mEthnicity = findViewById(R.id.Ethnicity);
        mGender = findViewById(R.id.Gender);
        mEducation = findViewById(R.id.Education);
        mYear = findViewById(R.id.Year);
        mifYes = findViewById(R.id.Ifyes);
        mKind = findViewById(R.id.kind);
        mHealth = findViewById(R.id.Health);
        mNext = findViewById(R.id.next);
        mDemographics = new HashMap<>();
        mHave = findViewById(R.id.Have);
        mCurrent = findViewById(R.id.Current);
        mNever = findViewById(R.id.Never);
        mYes = findViewById(R.id.yes);
        mNo = findViewById(R.id.no);
        mUserage = findViewById(R.id.Userage);
        mMale = findViewById(R.id.Male);
        mFemale = findViewById(R.id.Female);
        mNew = findViewById(R.id.New);
        mp = findViewById(R.id.Prefer1);
        mAge = findViewById(R.id.Agebutton);
        mp5 = findViewById(R.id.Prefer2);
        mWhite = findViewById(R.id.White);
        mBlack = findViewById(R.id.Black);
        mAsian = findViewById(R.id.Asian);
        mHispanic = findViewById(R.id.Hispanic);
        mOther = findViewById(R.id.Other);
        mp3 = findViewById(R.id.Prefer3);
        mNative = findViewById(R.id.Native);
        mUCSD = findViewById(R.id.UCSD);
        mCollege = findViewById(R.id.College);
        mN = findViewById(R.id.N);
        mp4 = findViewById(R.id.Prefer4);
        m1 = findViewById(R.id.One);
        m2 = findViewById(R.id.Two);
        m3 = findViewById(R.id.Three);
        m4 = findViewById(R.id.Four);
        m5 = findViewById(R.id.Five);
        mp2 = findViewById(R.id.Prefer5);
        mNA = findViewById(R.id.NA);

        boxes[0] = findViewById(R.id.checkBox);
        boxes[1] = findViewById(R.id.checkBox2);
        boxes[2] = findViewById(R.id.checkBox3);
        boxes[3] = findViewById(R.id.checkBox4);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                String mUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDemographics.put("Demographics", demo);
                try {
                    demo = (ArrayList<String>) EncUtil.encMap(mDemographics, mUser).get("Demographics");
                } catch (Exception e) {
                    //TODO
                }

                mDatabase.child("Users").child(mUser).child("Demographics").setValue(demo);
                mDatabase.child("Users").child(mUser).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                Intent intentLoadNewActivity = new Intent(Demographics.this, PHQ9.class);
                //intentLoadNewActivity.putExtra("", mDemographics);
                startActivity(intentLoadNewActivity);
            }
        });

        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        demo.add(mMale.getText().toString());
                        break;
                    case 1:
                        demo.add(mFemale.getText().toString());
                        break;
                    case 2:
                        demo.add(mNew.getText().toString());
                        break;
                    case 3:
                        demo.add(mp.getText().toString());
                        break;
                }
            }
        });

        String age = mUserage.getEditableText().toString();
        if (age != null) {
            demo.add(age);
        }
        mAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        demo.add(mp5.getText().toString());
                        break;
                }
            }
        });


        mEthnicity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        demo.add(mWhite.getText().toString());
                        break;
                    case 1:
                        demo.add(mHispanic.getText().toString());
                        break;
                    case 2:
                        demo.add(mBlack.getText().toString());
                        break;
                    case 3:
                        demo.add(mNative.getText().toString());
                        break;
                    case 4:
                        demo.add(mAsian.getText().toString());
                        break;
                    case 5:
                        demo.add(mOther.getText().toString());
                        break;
                    case 6:
                        demo.add(mp3.getText().toString());
                        break;
                }
            }
        });


        mEducation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        demo.add(mUCSD.getText().toString());
                        break;
                    case 1:
                        demo.add(mCollege.getText().toString());
                        break;
                    case 2:
                        demo.add(mN.getText().toString());
                        break;
                    case 3:
                        demo.add(mp4.getText().toString());
                        break;
                }
            }
        });

        mYear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        demo.add(m1.getText().toString());
                        break;
                    case 1:
                        demo.add(m2.getText().toString());
                        break;
                    case 2:
                        demo.add(m3.getText().toString());
                        break;
                    case 3:
                        demo.add(m4.getText().toString());
                        break;
                    case 4:
                        demo.add(m5.getText().toString());
                        break;
                    case 5:
                        demo.add(mNA.getText().toString());
                        break;
                    case 6:
                        demo.add(mp2.getText().toString());
                        break;
                }
            }
        });

        mHealth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0: // first button
                        demo.add(mYes.getText().toString());
                        break;
                    case 1:
                        demo.add(mYes.getText().toString());
                        break;
                }
            }
        });

        mKind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        demo.add(mHave.getText().toString());
                        break;

                    case 1:
                        demo.add(mCurrent.getText().toString());
                        break;

                    case 2:
                        demo.add(mNever.getText().toString());
                        break;

                    case 3:
                        demo.add(mp2.getText().toString());
                        break;

                }
            }
        });
        for (int i = 0; i < boxes.length; i++) {
            final CheckBox box = boxes[i];
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        demo.add(box.getText().toString());
                    } else {
                        demo.remove(box.getText().toString());
                    }
                }
            });
        }
    }
}
