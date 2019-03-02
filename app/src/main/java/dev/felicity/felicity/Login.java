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
    private final static int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);

        /*//google sign in options and stuff
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.

        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);


        //google button signin
        button = findViewById(R.id.googleBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sign in stuff
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });*/


        mEmail= findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLoginButton= findViewById(R.id.login);
        mAuth= FirebaseAuth.getInstance();
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
                startSignIn();
            }
        });

        mAuthListener= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@android.support.annotation.NonNull FirebaseAuth firebaseAuth){
              if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified()) {
                  startActivity(new Intent(Login.this, LandingPage.class));
                  finish();
              }

            }
        };

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(getApplicationContext(), ContactsContract.Profile.class   );
                            startActivity(i);
                            finish();
                            Toast.makeText(getApplicationContext(),"User logged in successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Could not log in user", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        //google login stuff
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //google login stuff

        mAuth.addAuthStateListener(mAuthListener);
        FirebaseUser currentUser = mFacebook.getCurrentUser();
    }*/

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
                            startActivity(new Intent(Login.this, LandingPage.class));
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
