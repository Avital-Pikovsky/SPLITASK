package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.ListAdapter;

public class friendsLists extends AppCompatActivity {
    private TextView returnBack, joinList;
    private ImageButton Refresh;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        setupUI();

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(friendsLists.this, LoggedInProfile.class));
            }
        });

        joinList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(friendsLists.this, LoggedInProfile.class));
            }
        });

        //Restarts the page to load list from DB.
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(friendsLists.this, friendsLists.class));
            }
        });

        //looping on ALL the database looking for list that the user is part of.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void setupUI() {

        returnBack = (TextView) findViewById(R.id.returnK);
        joinList = (TextView) findViewById(R.id.join);
        Refresh = (ImageButton) findViewById(R.id.ref);

    }

}