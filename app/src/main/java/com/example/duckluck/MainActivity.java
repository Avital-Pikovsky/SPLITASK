package com.example.duckluck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private Button signInButton;
    private TextView userSignUp, forgotPassword;
    private CheckBox check;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //to not login again
//        if (user != null) {
//            finish();
//            startActivity(new Intent(MainActivity.this, LoggedInProfile.class));
//        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(userEmail.getText().toString(),userPassword.getText().toString());

                
                startActivity(new Intent(MainActivity.this, LoggedInProfile.class));

            }
        });
        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoggedInProfile.class));
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //forgot password
                startActivity(new Intent(MainActivity.this, LoggedInProfile.class));

            }
        });

    }

    private void setupUI(){
        userEmail = (EditText) findViewById(R.id.mainUserEmail);
        userPassword = (EditText) findViewById(R.id.mainUserPassword);
        signInButton = (Button) findViewById(R.id.signIn);
        userSignUp = (TextView) findViewById(R.id.register);
        forgotPassword = (TextView) findViewById(R.id.ForgotPass);
        check = (CheckBox) findViewById(R.id.checkBox);

    }
    private void signIn(String userEmail, String userPassword){

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, LoggedInProfile.class));
                    Toast.makeText(MainActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}