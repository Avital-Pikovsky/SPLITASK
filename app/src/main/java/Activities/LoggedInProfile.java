package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedInProfile extends AppCompatActivity {

    private TextView myProfile, myHistory, lists, contactUs, signOut, adminBtn;
    private ImageView crown;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_profile);
        setupUI();
        CheckIfAdmin();

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoggedInProfile.this, myProfile.class));
            }
        });

        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoggedInProfile.this, CreatedLists.class));
            }
        });
        lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggedInProfile.this, JoinedLists.class));
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
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggedInProfile.this, AdminActivity.class));

            }
        });



    }
    private void CheckIfAdmin() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("User details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("isAdmin").getValue() != null) {
                    String flag = snapshot.child("isAdmin").getValue().toString();
                    if (flag.compareTo("true") == 0) {
                        adminBtn.setVisibility(View.VISIBLE);
                        crown.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setupUI(){
        myProfile = (TextView) findViewById(R.id.myPro);
        myHistory = (TextView) findViewById(R.id.myLists);
        lists = (TextView) findViewById(R.id.friendsLists);
        contactUs = (TextView) findViewById(R.id.contact);
        signOut = (TextView) findViewById(R.id.signO);
        adminBtn = (TextView) findViewById(R.id.Admin);
        crown = (ImageView) findViewById(R.id.crownId);
    }

}
