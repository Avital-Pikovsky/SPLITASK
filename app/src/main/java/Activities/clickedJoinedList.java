package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.ListAdapter;
import Adapters.UserProfile;

public class clickedJoinedList extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();
    private final DatabaseReference createdList = firebaseDatabase.getReference().child(firebaseAuth.getUid()).child("User details");
    private TextView JoinedListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_joined_lists);
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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                createdList.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        UserProfile up = snapshot.getValue(UserProfile.class);
                                        String name = up.getUserName();
                                        clickedList.set(clickedList.indexOf(clickedItem), clickedItem+" By: "+name);
                                        arrayAdapter.notifyDataSetChanged();
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot user : snapshot.getChildren()) {
                                                    for (DataSnapshot userLists : user.child("Created lists").getChildren()) {
                                                        ListAdapter LA = userLists.getValue(ListAdapter.class);

                                                        if (keyNumber == LA.getId()) {
                                                            LA.setList(clickedList);
                                                            DatabaseReference item = databaseReference.child(user.getKey()).child("Created lists").child(userLists.getKey());
                                                            item.setValue(LA);

                                                        }

                                                    }
                                                }                                            }

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
                AlertDialog.Builder builder = new AlertDialog.Builder(clickedJoinedList.this);
                builder.setMessage("Do you want to choose " + clickedItem + "?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot allDB) {
                for (DataSnapshot user : allDB.getChildren()) {
                    for (DataSnapshot userLists : user.child("Created lists").getChildren()) {
                        ListAdapter LA = userLists.getValue(ListAdapter.class);

                        if (keyNumber == LA.getId()) {
                            JoinedListName.setText(LA.getName());
                            for (int j = 0; j < LA.getList().size(); j++) {
                                clickedList.add((LA.getList().get(j)));
                            }
                            arrayAdapter.notifyDataSetChanged();
                        }
                        arrayAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupUI() {
        JoinedListName = (TextView) findViewById(R.id.freindListName);
    }
}