package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private TextView returnKey;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setupUI();

        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = passwordEmail.getText().toString().trim();

                if (user_email.isEmpty()) {
                    Toast.makeText(ForgotPassword.this, "Please enter your registered email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                            } else {
                                Toast.makeText(ForgotPassword.this, "Error in sending reset email!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
        returnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
            }
        });
    }

    private void setupUI() {
        passwordEmail = (EditText) findViewById(R.id.passwordEmail);
        resetPassword = (Button) findViewById(R.id.btnPasswordReset);
        returnKey = (TextView) findViewById(R.id.returnButton);
    }
}