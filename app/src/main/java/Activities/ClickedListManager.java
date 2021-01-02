package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.ListAdapter;
import Adapters.UserProfile;

public class ClickedListManager extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Created lists");
    private final DatabaseReference userD = firebaseDatabase.getReference(firebaseAuth.getUid()).child("User details");

    private TextView listName;
    private Button invite, Edit;
    private String name, listID;

    //This Activity is a dynamic activity.
    //It contains a List, and fill it by the current list that have been clicked in the previous page.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_list_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setupUI();

        String key = getIntent().getExtras().getString("listKey");
        key.replaceAll("\\s", "");
        int keyNumber = Integer.parseInt(key);

        final ListView list = findViewById(R.id.listClicked);
        ArrayList<String> clickedList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clickedList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                if (clickedItem.contains("√ ")) {
                    Toast.makeText(ClickedListManager.this, "Item is already checked", Toast.LENGTH_SHORT).show();

                } else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    userD.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            UserProfile up = snapshot.getValue(UserProfile.class);
                                            String name = up.getUserName();
                                            clickedList.set(clickedList.indexOf(clickedItem), "√ " + clickedItem + " - " + name);
                                            arrayAdapter.notifyDataSetChanged();
                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot list : snapshot.getChildren()) {
                                                        ListAdapter LA = list.getValue(ListAdapter.class);

                                                        if (keyNumber == LA.getId()) {
                                                            LA.setList(clickedList);
                                                            DatabaseReference item = databaseReference.child(list.getKey());
                                                            item.setValue(LA);

                                                        }

                                                    }
                                                }


                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClickedListManager.this);
                    builder.setMessage("Do you want to choose " + clickedItem + "?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

                }
            }

        });

        //Looking for a match up between a list and the key.
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    ListAdapter LA = uniqueUserSnapshot.getValue(ListAdapter.class);
                    if (LA.getId() == keyNumber) {
                        listName.setText(LA.getName());
                        name = listName.getText().toString();
                        listID = LA.getId() + "";

                        for (int j = 0; j < LA.getList().size(); j++) {
                            clickedList.add((LA.getList().get(j)));
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClickedListManager.this, EditListByManager.class);
                i.putExtra("listKey", key);
                startActivity(i);
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClickedListManager.this, Invite_Search.class);
                i.putExtra("name", name);
                i.putExtra("ID", listID);
                startActivity(i);
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

    private void setupUI() {
        listName = (TextView) findViewById(R.id.freindListName);
        invite = (Button) findViewById(R.id.invites);
        Edit = (Button) findViewById(R.id.edit);

    }
}