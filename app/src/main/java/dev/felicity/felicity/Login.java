package dev.felicity.felicity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.support.annotation.NonNull;

public class Login extends AppCompatActivity {


    private Button mLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private android.widget.TextView mSignup;
    private android.widget.TextView mForgotPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail= findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLoginButton= findViewById(R.id.login);
        mAuth= FirebaseAuth.getInstance();
        mSignup= findViewById(R.id.signup);
        mForgotPassword = findViewById(R.id.forgotPassword);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        mAuthListener= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@android.support.annotation.NonNull FirebaseAuth firebaseAuth){
              if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified()){
                    startActivity(new Intent(Login.this, LandingPage.class));
                    finish();
                }
            }
        };

    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String email= mEmail.getText().toString();
        String password= mPassword.getText().toString();

        if(android.text.TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(Login.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                    }
                    else if(!mAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(Login.this,"You must verify email first",Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }

    public void goToSignUp(View v){
        startActivity(new Intent(Login.this, Signup.class));
    }

    public void goToForgotPassword(View v) {
        startActivity(new Intent(Login.this, ForgotPassword.class));
    }

}
