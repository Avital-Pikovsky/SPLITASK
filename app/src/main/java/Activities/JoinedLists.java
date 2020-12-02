package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.JoinedListAdapter;
import Adapters.ListAdapter;


public class JoinedLists extends AppCompatActivity implements AddIdOfListDialog.OnInputListener {

    private static final String TAG = "JoinedLists";

    private TextView returnBack, joinList;
    private ImageButton Refresh;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference JoinedListsRef = databaseReference.child(firebaseAuth.getUid()).child("Joined lists");
    public TextView mInputDisplay;
    public String keyInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        setupUI();

        final ListView list = findViewById(R.id.list);
        ArrayList<String> friendListHistory = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendListHistory);
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                String sp[] = clickedItem.split("ID:");
                String key = sp[1];
                Intent i = new Intent(JoinedLists.this, clickedJoinedList.class);
                i.putExtra("listKey", key);
                startActivity(i);
            }
        });

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(JoinedLists.this, LoggedInProfile.class));
            }
        });

        //The user put unique ID, probably through some kind of simple dialog.
        //This ID got searched in the whole DB and the right list added to the friendListHistory list.
        joinList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog.");
                AddIdOfListDialog dialog = new AddIdOfListDialog();
                dialog.show(getFragmentManager(), "AddIdOfListDialog");

                friendListHistory.add(keyInput);
                //need to find the list by ID
                //and add it to the person calling
                JoinedListAdapter JLA = new JoinedListAdapter(keyInput);
                DatabaseReference newListRef = JoinedListsRef.push();
                newListRef.setValue(JLA);

            }
        });

        //Restarts the page to load list from DB.
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        //looping on ALL the database looking for list that the user is part of.
        JoinedListsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                for (DataSnapshot uniqueUserSnapshot1 : dataSnapshot1.getChildren()) {
                    JoinedListAdapter JLA = uniqueUserSnapshot1.getValue(JoinedListAdapter.class);
                    arrayAdapter.notifyDataSetChanged();

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            for (DataSnapshot uniqueUserSnapshot2 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot uniqueUserSnapshot3 : uniqueUserSnapshot2.child("Created lists").getChildren()) {
                                    ListAdapter LA = uniqueUserSnapshot3.getValue(ListAdapter.class);

                                    if (JLA.getId().equals(LA.getId() + "")) {
                                        friendListHistory.add(LA.getName() + " ID:" + LA.getId());
                                        arrayAdapter.notifyDataSetChanged();

                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void setupUI() {

        returnBack = (TextView) findViewById(R.id.returnK);
        joinList = (TextView) findViewById(R.id.join);
        Refresh = (ImageButton) findViewById(R.id.ref);
        mInputDisplay = findViewById(R.id.input_display);
    }

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: got the input: " + input);
//        mInputDisplay.setText(input);
    }
}