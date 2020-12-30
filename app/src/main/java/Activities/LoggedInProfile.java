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

    private TextView myProfile, myHistory, lists, contactUs, signOut;
    private ImageView crown;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
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
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
    private void setupUI(){
        myProfile = (TextView) findViewById(R.id.myPro);
        myHistory = (TextView) findViewById(R.id.myLists);
        lists = (TextView) findViewById(R.id.friendsLists);
        contactUs = (TextView) findViewById(R.id.contact);
        signOut = (TextView) findViewById(R.id.signO);
    }

}
