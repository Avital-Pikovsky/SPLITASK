package com.example.duckluck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoggedInProfile extends AppCompatActivity {

    private TextView myProfile, myHistory, lists, contactUs, signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_profile);
        setupUI();

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoggedInProfile.this, myProfile.class));
            }
        });

        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoggedInProfile.this, myLists.class));
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoggedInProfile.this, contactUs.class));
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoggedInProfile.this, MainActivity.class));
            }
        });

        lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggedInProfile.this, freindsLists.class));
            }
        });

        }
    private void setupUI(){
        myProfile = (TextView) findViewById(R.id.myPro);
        myHistory = (TextView) findViewById(R.id.myLists);
        lists = (TextView) findViewById(R.id.friendsLists);
        contactUs = (TextView) findViewById(R.id.contact);
        signOut = (TextView) findViewById(R.id.signO);
    }

}
