package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private EditText newPassword;
    private Button update;

    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setupUI();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userPasswordNew = newPassword.getText().toString();
                if(userPasswordNew.length() < 6){
                    Toast.makeText(UpdatePassword.this, "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(UpdatePassword.this, myProfile.class));
                                Toast.makeText(UpdatePassword.this, "Password Changed!", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(UpdatePassword.this, "Password Update Failed!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
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
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
    newPassword = (EditText) findViewById(R.id.etNewPass);
    update = (Button) findViewById(R.id.btnChangePassw);
    }
}