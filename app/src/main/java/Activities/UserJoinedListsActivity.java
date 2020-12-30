package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.JoinedListAdapter;
import Adapters.ListAdapter;
import Adapters.UserProfile;

public class UserJoinedListsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();
    private final DatabaseReference databaseReference2 = firebaseDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_joined_lists);

        String clickedUser = getIntent().getExtras().getString("userName");


        //Creating a Linear view List to be shown in this activity.
        final ListView list = findViewById(R.id.listt);
        ArrayList<String> listHistory = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listHistory);
        list.setAdapter(arrayAdapter);

        //fill the list from the database, with id.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot allDB) {
                for (DataSnapshot user : allDB.getChildren()) {
                    DataSnapshot currentUser = user.child("User details");
                    UserProfile up = currentUser.getValue(UserProfile.class);

                    if(up.getUserName().equals(clickedUser)) {
                        DataSnapshot userLists = user.child("Joined lists");
                        for (DataSnapshot list: userLists.getChildren()) {
                            JoinedListAdapter JLA = list.getValue(JoinedListAdapter.class);
                            arrayAdapter.notifyDataSetChanged();

                            databaseReference2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                    for (DataSnapshot uniqueUserSnapshot2 : dataSnapshot2.getChildren()) {
                                        for (DataSnapshot uniqueUserSnapshot3 : uniqueUserSnapshot2.child("Created lists").getChildren()) {
                                            ListAdapter LA = uniqueUserSnapshot3.getValue(ListAdapter.class);

                                            if (JLA.getId().equals(LA.getId() + "")) {
                                                listHistory.add(LA.getName() + " ID:" + LA.getId());
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //When item get clicked, move its key to the next page.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                String sp[] = clickedItem.split("ID:");
                String key = sp[1];
                Intent i = new Intent(UserJoinedListsActivity.this, UserViewList.class);
                i.putExtra("listKey", key);
                startActivity(i);
            }
        });


    }

}