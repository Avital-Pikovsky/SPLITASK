package com.example.duckluck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myProfile extends AppCompatActivity {

    private ImageView profilePic;
    private TextView returnBack, profileName, profileEmail, profilePhone;
    private Button profileUpdate, changePassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setupUI();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                profileName.setText("Name: " + userProfile.getUserName());
                profileEmail.setText("Email: " + userProfile.getUserEmail());
                profilePhone.setText("Phone Number: " + userProfile.getUserPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(myProfile.this, error.getCode(), Toast.LENGTH_SHORT).show();

            }
        });
        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myProfile.this, UpdateProfile.class));
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myProfile.this, UpdatePassword.class));
            }
        });

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myProfile.this, LoggedInProfile.class));
            }
        });
    }

    private void setupUI() {

        returnBack = (TextView) findViewById(R.id.returnTextView);
        profilePic = (ImageView) findViewById(R.id.tvProfilePic);
        profileName = (TextView) findViewById(R.id.tvProfileName);
        profileEmail = (TextView) findViewById(R.id.tvProfileEmail);
        profilePhone = (TextView) findViewById(R.id.tvProfilePhone);
        profileUpdate = (Button) findViewById(R.id.btnProfileUpdate);
        changePassword = (Button) findViewById(R.id.btnChangePass);
    }

}