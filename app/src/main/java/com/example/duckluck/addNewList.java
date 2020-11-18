package com.example.duckluck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class addNewList extends AppCompatActivity {

    private EditText listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_list);
        setupUI();

    }

    private void setupUI() {
        listName = (EditText) findViewById(R.id.listname);
    }

}