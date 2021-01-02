package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.UserProfile;

public class SelectUserDetailsActivity extends AppCompatActivity {

    private Button userDetails_b, userLists_b, userJoinedLists_b, deleteUserBtn;
    private CheckBox makeUserToAdmin;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_details);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setupUI();

        String clickedUser = getIntent().getExtras().getString("userName");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot allDB) {
                for (DataSnapshot user : allDB.getChildren()) {
                    DataSnapshot currentUser = user.child("User details");
                    UserProfile up = currentUser.getValue(UserProfile.class);

                    if(up.getUserName().equals(clickedUser)) {
                        if (up.getIsAdmin().equals("true")) {
                            makeUserToAdmin.setChecked(true);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userDetails_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUserDetailsActivity.this, UserDetailsActivity.class);
                i.putExtra("userName", clickedUser );
                startActivity(i);
            }
        });
        userLists_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUserDetailsActivity.this, UserListsActivity.class);
                i.putExtra("userName", clickedUser );
                startActivity(i);

            }
        });
        userJoinedLists_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUserDetailsActivity.this, UserJoinedListsActivity.class);
                i.putExtra("userName", clickedUser );
                startActivity(i);
            }
        });
        makeUserToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot allDB) {
                        for (DataSnapshot user : allDB.getChildren()) {
                            DataSnapshot currentUser = user.child("User details");
                            UserProfile up = currentUser.getValue(UserProfile.class);

                            if(up.getUserName().equals(clickedUser)) {
                                Boolean check = makeUserToAdmin.isChecked();
                                if (check) {
                                    up.setIsAdmin("true");
                                }
                                else{
                                    up.setIsAdmin("false");
                                }
                                DatabaseReference d = databaseReference.child(user.getKey()).child("User details");
                                d.setValue(up);


                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot allDB) {
                                        for (DataSnapshot user : allDB.getChildren()) {
                                            DataSnapshot currentUser = user.child("User details");
                                            UserProfile up = currentUser.getValue(UserProfile.class);

                                            if(up.getUserName().equals(clickedUser)) {
                                                DatabaseReference d = databaseReference.child(user.getKey());
                                                d.removeValue();
                                                startActivity(new Intent(SelectUserDetailsActivity.this, AdminActivity.class));
                                                Toast.makeText(SelectUserDetailsActivity.this, "You deleted "+clickedUser , Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectUserDetailsActivity.this);
                builder.setMessage("Are you sure that you want to delete " + clickedUser + "?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();


            }



        });
    }



    private void setupUI(){
        userDetails_b = (Button) findViewById(R.id.userDetailsBtn);
        userLists_b = (Button) findViewById(R.id.userListsBtn);
        userJoinedLists_b = (Button) findViewById(R.id.friendsListsBtn);
        makeUserToAdmin = (CheckBox) findViewById(R.id.makeAdmin);
        deleteUserBtn = (Button) findViewById(R.id.deleteUser);


    }
}