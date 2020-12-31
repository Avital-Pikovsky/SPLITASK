package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class contactUs extends AppCompatActivity {

    private EditText message, subject;
    private Button send;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setupUI();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_subject = subject.getText().toString().trim();
                String str_message = message.getText().toString().trim();
                String email = "Splitask2020@gmail.com";

                if(str_message.isEmpty() || str_subject.isEmpty()){
                    Toast.makeText(contactUs.this, "Please enter subject and message", Toast.LENGTH_SHORT).show();

                }
                else{
                    String mail = "mailto:" + email + "?&subject=" + Uri.encode(str_subject) + "&body" + Uri.encode(str_message);

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse(mail));

                    try{
                        startActivity(Intent.createChooser(intent, "Send Email.."));
                    }
                    catch (Exception e){
                        Toast.makeText(contactUs.this, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
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

        message = (EditText) findViewById(R.id.etMessage);
        subject = (EditText) findViewById(R.id.etSubject);
        send = (Button) findViewById(R.id.btnSend);
    }
}