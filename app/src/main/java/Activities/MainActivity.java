package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.UserProfile;

public class MainActivity extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private Button signInButton, main;
    private TextView userSignUp, forgotPassword;
    private CheckBox check;
    private String status = "User";

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupUI();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        allData = firebaseDatabase.getReference();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        //to not login again
//        if (user != null) {
//            finish();
//            startActivity(new Intent(MainActivity.this, LoggedInProfile.class));
//        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoggedInProfile.class));
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //forgot password
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));

            }
        });

        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }

        });

    }

    private String Admin() {

        Boolean checkBox = check.isChecked();
        allData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot allDataSnapshot) {
                for (DataSnapshot user : allDataSnapshot.getChildren()) {
                    DataSnapshot details = user.child("User details");
                    UserProfile currentUserProfile = details.getValue(UserProfile.class);
                    if (currentUserProfile.getIsAdmin() != null) {
                        if (currentUserProfile.getIsAdmin().equals("true")) {
                            if (checkBox) {
                                status = "trueAdmin";
                            } else {
                                status = "stupidAdmin";
                            }
                        } else {
                            if (checkBox) {
                                status = "stupidUser";
                            } else {
                                status = "user";
                            }
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "You are sjhdjasdhjasdhjasdhjas admin", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "You are sjhdjasdhjasdhjasdhjas admin", Toast.LENGTH_SHORT).show();

            }
        });

        return status;
    }

    private void setupUI() {
        userEmail = (EditText) findViewById(R.id.mainUserEmail);
        userPassword = (EditText) findViewById(R.id.mainUserPassword);
        signInButton = (Button) findViewById(R.id.signIn);
        userSignUp = (TextView) findViewById(R.id.register);
        forgotPassword = (TextView) findViewById(R.id.ForgotPass);
        check = (CheckBox) findViewById(R.id.checkBox2);
        main = (Button) findViewById(R.id.button2);

    }

    private void signIn(String userEmail, String userPassword) {
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        String answer = Admin();
                        if (answer.equals("user")) {
                            startActivity(new Intent(MainActivity.this, LoggedInProfile.class));
                            Toast.makeText(MainActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                        }
                        if (answer.equals("trueAdmin")) {
                            startActivity(new Intent(MainActivity.this, AdminActivity.class));
                            Toast.makeText(MainActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                        }
                        if (answer.equals("stupidAdmin")) {
                            Toast.makeText(MainActivity.this, "You are admin", Toast.LENGTH_SHORT).show();

                        }
                        if (answer.equals("stupidUser")) {
                            Toast.makeText(MainActivity.this, "You need to unCheck admin", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}