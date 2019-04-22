package dev.felicity.felicitycbt;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    private android.widget.Button mSignup;
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmail= findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mSignup= findViewById(R.id.signup);
        mAuth= FirebaseAuth.getInstance();

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popDialog();
            }
        });

    }
    private void signUp(){
        String email= mEmail.getText().toString();
        String password= mPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(Signup.this,"Sign Up Failed",Toast.LENGTH_LONG).show();
                        }

                        else {
                            final FirebaseUser user= mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(Signup.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup.this,"Verify Email",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(Signup.this,"Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            mAuth.signOut();
                            startActivity(new Intent(Signup.this, Login.class));
                            finish();
                        }
                    }
                });

    }

    public void popDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Terms and Conditions");
        alertDialogBuilder.setMessage(R.string.termsUpdated);
        alertDialogBuilder.setPositiveButton("Accept",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        signUp();
                    }
                });

        alertDialogBuilder.setNegativeButton("Decline",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
