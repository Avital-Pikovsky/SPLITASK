package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectUserDetailsActivity extends AppCompatActivity {

    private Button userDetails_b, userLists_b, userJoinedLists_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_details);

        setupUI();

        String clickedUser = getIntent().getExtras().getString("userName");

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
    }
    private void setupUI(){
        userDetails_b = (Button) findViewById(R.id.userDetailsBtn);
        userLists_b = (Button) findViewById(R.id.userListsBtn);
        userJoinedLists_b = (Button) findViewById(R.id.friendsListsBtn);

    }
}