package dev.felicity.felicitycbt;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class LandingPage extends AppCompatActivity {


    private Button mSessionButton;
    private Button mSignout;
    private Button mPHQ9;
    //private Button mBlankSessionButton;
    //private DatabaseReference mDatabase;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        //hamburger menu stuff
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                Toast.makeText(LandingPage.this, "Auth went wrong", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        //toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSessionButton = findViewById(R.id.session);
        mSignout=findViewById(R.id.logout);
        mPHQ9=findViewById(R.id.phq9);
        //mBlankSessionButton=findViewById(R.id.blankSession);

        mSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(LandingPage.this, CbtIntro.class);
                startActivity(intentLoadNewActivity);
            }
        });

        mPHQ9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(LandingPage.this, PHQ9.class);
                startActivity(intentLoadNewActivity);
            }
        });

       mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
                com.facebook.login.LoginManager.getInstance().logOut();
                Intent intentLoadNewActivity = new Intent(LandingPage.this, Login.class);
                intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLoadNewActivity);
                finish();
            }
        });

       /*mBlankSessionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intentLoadNewActivity = new Intent(LandingPage.this, BlankSession.class);
               startActivity(intentLoadNewActivity);
           }
       });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
