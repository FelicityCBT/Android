package dev.felicity.felicitycbt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.HashMap;

public class WelcomeVideo extends AppCompatActivity {

    private VideoView vid;
    private MediaController m;
    private ImageButton mNext;
    private HashMap<String, Object> mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_video);

        mInfo= new HashMap<String, Object>();

        // Connect object w/ elements
        vid = findViewById(R.id.videoView);
        playVideo(vid); // Plays video asap

        // Next button
        mNext = findViewById(R.id.next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(WelcomeVideo.this, TermsAndConditions.class);
                intentLoadNewActivity.putExtra("mInfo", mInfo);
                startActivity(intentLoadNewActivity);
            }
        });

    }


    // Play video
    public void playVideo(View v) {
        // Set new MediaController
        m = new MediaController(this);
        vid.setMediaController(m);

        String path = "android.resource://dev.felicity.felicitycbt/"+R.raw.welcome_video;
        Uri u = Uri.parse(path);

        vid.setVideoURI(u);

        vid.start();


    }
}
