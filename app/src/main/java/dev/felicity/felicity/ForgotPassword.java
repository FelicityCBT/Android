package dev.felicity.felicity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private Button rResetPasswordBtn;
    private EditText rForgotPasswordEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        rResetPasswordBtn = findViewById(R.id.resetPassword);
        rForgotPasswordEmail = findViewById(R.id.forgotPasswordEmail);

        mAuth = FirebaseAuth.getInstance();

        rResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = rForgotPasswordEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this,
                                        "Email sent! Please check your inbox.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPassword.this,
                                        "Failed to send email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
