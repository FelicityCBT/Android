package dev.felicity.felicity;

import android.provider.ContactsContract;
import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.signin.SignIn;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import android.support.annotation.NonNull;

public class Login extends AppCompatActivity {


    private Button mLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private android.widget.TextView mSignup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    SignInButton button;
    private final static int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //google button signin
        button = findViewById(R.id.googleBtn);
        //moved this line from down there up
        mAuth= FirebaseAuth.getInstance();
        //google sign in options and stuff
        //step 1
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("961359328033-jhlaa6pl6gdh2trrn2vjpgm8sfn8fekh.apps.googleusercontent.com")
                .requestEmail()
                .build();
        //step 2
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mEmail= findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLoginButton= findViewById(R.id.login);
        mSignup= findViewById(R.id.signup);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        mAuthListener= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@android.support.annotation.NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser()!=null /*&& firebaseAuth.getCurrentUser().isEmailVerified()*/){
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //copied from firebase
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(Login.this, "Auth went wrong", Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
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

}
