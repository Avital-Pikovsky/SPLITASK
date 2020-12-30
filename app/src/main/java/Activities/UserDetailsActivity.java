package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.UserProfile;

public class UserDetailsActivity extends AppCompatActivity {

    private TextView UserName, UserEmail, UserPhone;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        setupUI();

        String clickedUser = getIntent().getExtras().getString("userName");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot allDB) {
                for (DataSnapshot user : allDB.getChildren()) {
                    DataSnapshot currentUser = user.child("User details");
                    UserProfile up = currentUser.getValue(UserProfile.class);

                    if(up.getUserName().equals(clickedUser)) {
                        UserName.setText("Name: " + up.getUserName());
                        UserEmail.setText("Email: " + up.getUserEmail());
                        UserPhone.setText("Phone Number: " + up.getUserPhone());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setupUI() {

        UserName = (TextView) findViewById(R.id.UserNameId);
        UserEmail = (TextView) findViewById(R.id.UserEmailId);
        UserPhone = (TextView) findViewById(R.id.UserPhoneId);
    }

}