package com.example.duckluck;

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

public class MainActivity extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private Button signInButton;
    private TextView userSignUp, forgotPassword;
    private CheckBox check;
    private Switch managementUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    //check if register and go to main profile
                }
            }
        });
        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));

            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //forgot password
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
        managementUser = (Switch) findViewById(R.id.mangement);


    }
    private Boolean validation(){
        Boolean result = false;

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        boolean checked = check.isChecked();


        if(email.isEmpty() && password.isEmpty() && !checked){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
}