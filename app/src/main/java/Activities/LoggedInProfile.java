package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.UserProfile;

public class LoggedInProfile extends AppCompatActivity {

    private TextView myProfile, myHistory, lists, contactUs, signOut;
    private static Boolean flag = false;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth;
    private final DatabaseReference allData = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        setupUI();

        firebaseAuth = FirebaseAuth.getInstance();

        allData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot name = snapshot.child(firebaseAuth.getUid()).child("User details");
                UserProfile u = name.getValue(UserProfile.class);
                String myName = u.getUserName();
                int counter = 0;
                DataSnapshot invites = snapshot.child(firebaseAuth.getUid()).child("Pending invitation");
                for (DataSnapshot inv : invites.getChildren()) {
                    counter++;
                }
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoggedInProfile.this);

                if(!flag) {
                    flag = true;
                    if (counter != 0)
                        builder.setMessage("Hello " + myName + "!\nYou have " + counter + " new invitations ").create().show();
                    else
                        builder.setMessage("Hello " + myName + "!\nwe missed you :)").create().show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                firebaseAuth.signOut();
                startActivity(new Intent(LoggedInProfile.this, MainActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile:
                startActivity(new Intent(this, myProfile.class));
                break;
            case R.id.my_lists:
                startActivity(new Intent(this, CreatedLists.class));
                break;
            case R.id.friends_lists:
                startActivity(new Intent(this, JoinedLists.class));
                break;
            case R.id.contact:
                startActivity(new Intent(this, contactUs.class));
                break;
            case R.id.out:
                firebaseAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        myProfile = (TextView) findViewById(R.id.myPro);
        myHistory = (TextView) findViewById(R.id.myLists);
        lists = (TextView) findViewById(R.id.friendsLists);
        contactUs = (TextView) findViewById(R.id.contact);
        signOut = (TextView) findViewById(R.id.signO);
    }

}
