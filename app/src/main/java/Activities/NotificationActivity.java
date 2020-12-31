package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
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
import Adapters.pendingInvite;

public class NotificationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference invitesRef = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Pending invitation");
    private final DatabaseReference invitesRef2 = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Pending invitation");
    private final DatabaseReference joinedListsRef = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Joined lists");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Creating a Linear view List to be shown in this activity.
        final ListView list = findViewById(R.id.list);
        ArrayList<String> inviteList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inviteList);
        list.setAdapter(arrayAdapter);

        //When item get clicked, move its key to the next page.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                String[] split = clickedItem.split("ID: ");
                String listID = split[1];
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                DatabaseReference pushedList = joinedListsRef.push();
                                JoinedListAdapter j = new JoinedListAdapter(listID);
                                pushedList.setValue(j);


                                break;

                            case DialogInterface.BUTTON_NEGATIVE:


                                break;
                        }
                    }

                };
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificationActivity.this);
                builder.setMessage("Do you want to join this list? ").

                        setPositiveButton("Yes", dialogClickListener).

                        setNegativeButton("No", dialogClickListener).

                        show();

                invitesRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                            pendingInvite pen = uniqueUserSnapshot.getValue(pendingInvite.class);
                            if (pen.getListId().equals(listID)) {
                                String key = uniqueUserSnapshot.getKey();
                                invitesRef2.child(key).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });


        //fill the list from the database, with id.
        invitesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                inviteList.clear();
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    pendingInvite pen = uniqueUserSnapshot.getValue(pendingInvite.class);
                    inviteList.add("List: " + pen.getListName() + " Creator: " + pen.getCreatorName() + " ID: " + pen.getListId());
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}