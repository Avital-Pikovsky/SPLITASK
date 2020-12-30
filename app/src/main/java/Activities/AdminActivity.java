package Activities;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.UserProfile;

public class AdminActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayList<String> listOfUsers;
    ArrayAdapter<String > adapter;

    private Button signOut;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference allData = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        signOut = (Button) findViewById(R.id.signOutId);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);

        listOfUsers = new ArrayList<>();


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listOfUsers);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(listOfUsers.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(AdminActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedUser = (String) listView.getItemAtPosition(position);
                Intent i = new Intent(AdminActivity.this, SelectUserDetailsActivity.class);
                i.putExtra("userName", clickedUser);
                startActivity(i);
            }
        });

        //fill the list from the database
        allData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot allDataSnapshot) {
                for (DataSnapshot user : allDataSnapshot.getChildren()) {
                    DataSnapshot details = user.child("User details");
                    UserProfile currentUserProfile = details.getValue(UserProfile.class);
                    listOfUsers.add(currentUserProfile.getUserName());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(AdminActivity.this, MainActivity.class));
            }
        });
    }

}