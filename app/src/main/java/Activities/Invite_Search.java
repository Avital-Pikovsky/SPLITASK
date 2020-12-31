package Activities;

//import android.support.v7.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Adapters.ListAdapter;
import Adapters.UserProfile;
import Adapters.pendingInvite;

public class Invite_Search extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayList<String> listOfUsers;
    ArrayAdapter<String> adapter;
    String owner = null;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference allData = firebaseDatabase.getReference();
    private final DatabaseReference allData2 = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite__search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);


        listOfUsers = new ArrayList<>();


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfUsers);
        listView.setAdapter(adapter);


        //When item get clicked, move its key to the next page.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedName = (String) listView.getItemAtPosition(position);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                allData2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot allDataSnapshot) {

                                        for (DataSnapshot user : allDataSnapshot.getChildren()) {
                                            DataSnapshot details = user.child("User details");
                                            UserProfile currentUserProfile = details.getValue(UserProfile.class);
                                            if (currentUserProfile.getUserName().equals(clickedName)) {
                                                DatabaseReference temp = allData2.child(user.getKey()).child("Pending invitation").push();
                                                String listName = getIntent().getExtras().getString("name");
                                                String ID = getIntent().getExtras().getString("ID");
                                                pendingInvite pinv = new pendingInvite(listName, owner, ID);
                                                DataSnapshot invites = allDataSnapshot.child(user.getKey()).child("Pending invitation");
                                                Boolean found = false;
                                                for (DataSnapshot inv : invites.getChildren()) {
                                                    pendingInvite p = inv.getValue(pendingInvite.class);
                                                    if(p.getListId().equals(pinv.getListId())){
                                                        found = true;
                                                    }

                                                }
                                                if(!found)
                                                temp.setValue(pinv);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Invite_Search.this);
                builder.setMessage("Do you want to invite " + clickedName + "?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();


            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (listOfUsers.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    Toast.makeText(Invite_Search.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });


        //fill the list from the database
        allData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot allDataSnapshot) {
                listOfUsers.clear();
                DataSnapshot thisUser = allDataSnapshot.child(firebaseAuth.getUid()).child("User details");
                UserProfile thisUserProfile = thisUser.getValue(UserProfile.class);
                owner = thisUserProfile.getUserName();
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
}