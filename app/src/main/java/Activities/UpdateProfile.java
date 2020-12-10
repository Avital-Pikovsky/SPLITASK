package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.UserProfile;

public class UpdateProfile extends AppCompatActivity {

    private EditText newUserName, newUserEmail, newUserPhone;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setupUI();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                newUserName.setText(userProfile.getUserName());
                newUserEmail.setText(userProfile.getUserEmail());
                newUserPhone.setText(userProfile.getUserPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfile.this, error.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        //update the user profile in the db.
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newUserName.getText().toString();
                String email = newUserEmail.getText().toString();
                String phone = newUserPhone.getText().toString();

                UserProfile userProfile = new UserProfile(name, email, phone);

                databaseReference.child("User details").setValue(userProfile);

                startActivity(new Intent(UpdateProfile.this, myProfile.class));
                Toast.makeText(UpdateProfile.this, "Changed Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setupUI() {
        newUserName = (EditText) findViewById(R.id.etNameUpdate);
        newUserEmail = (EditText) findViewById(R.id.etEmailUpdate);
        newUserPhone = (EditText) findViewById(R.id.etPhoneUpdate);
        save = (Button) findViewById(R.id.btnSave);
    }
}