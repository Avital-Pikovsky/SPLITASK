package com.example.duckluck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {

    private EditText userName,userEmail, userPassword;
    private Button regButton;
    private TextView userLogin;
    private CheckBox check;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUI();
        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {

                    firebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }
    private void setupUI(){
        userName = (EditText) findViewById(R.id.etUserName);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);
        check = (CheckBox) findViewById(R.id.checkBox);
    }
    private Boolean validate(){
        Boolean result = false;

        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        Boolean checkBox = check.isChecked();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if(!name.matches("^[a-zA-Z]+\\s[a-zA-Z\\s]+$")) {
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
        }
        else if(!email.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")){
            Toast.makeText(this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 6){
            Toast.makeText(this, "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show();
        }
        else if(!checkBox){
            Toast.makeText(this, "You need to agree to the Terms of Services and Privacy Policy.", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
}