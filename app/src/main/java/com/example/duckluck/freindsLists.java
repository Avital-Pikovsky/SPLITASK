package com.example.duckluck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class freindsLists extends AppCompatActivity {
    private TextView returnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(freindsLists.this, LoggedInProfile.class));
            }
        });
    }

    private void setupUI() {
        returnBack = (TextView) findViewById(R.id.returnK);
    }

}