package dev.felicity.felicity;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.signin.SignIn;
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
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;
import com.google.firebase.auth.R.*;
import com.facebook.AccessToken;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class Login extends AppCompatActivity {


    private static final String TAG = "Facebook Login";
    private Button mLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private android.widget.TextView mSignup;
    private android.widget.TextView mForgotPassword;
    private FirebaseAuth mAuth, mFacebook;
    private FirebaseAuth.AuthStateListener mAuthListener;
    LoginButton loginButton;
    CallbackManager callbackManager;
    SignInButton button;
    private final static int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private String TAG_GOOGLE = "MainActivity";
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        //google button signin
        button = findViewById(R.id.googleBtn);
        //moved this line from down there up
        mAuth= FirebaseAuth.getInstance();
//        google sign in options and stuff
        //step 1
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        //google sign in options and stuff
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("961359328033-jhlaa6pl6gdh2trrn2vjpgm8sfn8fekh.apps.googleusercontent.com")
                .requestEmail()
                .build();
        //step 2
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=2; //indicate google sign in
                signIn();
            }
        });


        mEmail= findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLoginButton= findViewById(R.id.login);
        mSignup= findViewById(R.id.signup);
        mForgotPassword = findViewById(R.id.forgotPassword);
        mFacebook= FirebaseAuth.getInstance();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;// indicate ep signin
                startSignIn();
            }
        });

        mAuthListener= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@android.support.annotation.NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified()){
                    String id= FirebaseAuth.getInstance().getUid();

                    if(flag==1) {
                        /** go to dem if first time **/
                        // Get a reference to our posts
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Users/" + id);
                        // Attach a listener to read the data at our posts reference
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.hasChild("Demographics")) {
                                    Intent intentLoadNewActivity = new Intent(Login.this, Demographics.class);
                                    startActivity(intentLoadNewActivity);
                                    finish();
                                } else if (dataSnapshot.hasChild("Demographics")) {
                                    startActivity(new Intent(Login.this, LandingPage.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                /*TODO: figure out if anything needs to go here*/
                            }
                        });
                    }
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

/*    //copied from firebase
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

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
                Log.w(TAG_GOOGLE, "Google sign in failed", e);
                Toast.makeText(Login.this, "Auth went wrong", Toast.LENGTH_LONG).show();
                // ...
            }
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
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
                            Log.d(TAG_GOOGLE, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if(isNewUser){
                                popDialog();
                                //finish();
                            }
                            else{
                                startActivity(new Intent(Login.this, LandingPage.class));
                                finish();
                            }
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_GOOGLE, "signInWithCredential:failure", task.getException());
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
                    } else {
                        startActivity(new Intent(Login.this, LandingPage.class));
                        finish();
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

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFacebook.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if(isNewUser){
                                popDialog();
                                //finish();
                            }
                            else {
                                startActivity(new Intent(Login.this, LandingPage.class));
                                finish();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    public void popDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Terms and Conditions");
        alertDialogBuilder.setMessage(R.string.termsUpdated);
        alertDialogBuilder.setPositiveButton("Accept",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(Login.this, Demographics.class));
                    }
                });
        alertDialogBuilder.setNegativeButton("Decline",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuth.getInstance().getCurrentUser().delete();
                    }
                });
        alertDialogBuilder.create().show();
    }
}
