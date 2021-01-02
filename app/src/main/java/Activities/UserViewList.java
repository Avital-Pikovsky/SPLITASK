package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.ListAdapter;

public class UserViewList extends AppCompatActivity {

    private EditText listName;
    private Button Change;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_list);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        listName = (EditText) findViewById(R.id.listName);
        Change = (Button) findViewById(R.id.changeName);

        String key = getIntent().getExtras().getString("listKey");
        key.replaceAll("\\s", "");
        int keyNumber = Integer.parseInt(key);

        final ListView list = findViewById(R.id.listClicked);
        ArrayList<String> clickedList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clickedList);
        list.setAdapter(arrayAdapter);


        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = listName.getText().toString();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot uniqueUserSnapshot : snapshot.getChildren()) {
                            DataSnapshot lists = uniqueUserSnapshot.child("Created lists");
                            for (DataSnapshot oneList : lists.getChildren()) {
                                ListAdapter l = oneList.getValue(ListAdapter.class);
                                if (l.getId() == keyNumber) {
                                    l.setName(name);
                                    DatabaseReference ref = databaseReference.child(uniqueUserSnapshot.getKey()).child("Created lists").child(oneList.getKey());
                                    ref.setValue(l);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if (clickedList.size() == 1) {
                                    clickedList.add("The list is empty");
                                }
                                clickedList.remove(clickedItem);
                                arrayAdapter.notifyDataSetChanged();


                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot user : snapshot.getChildren()) {
                                            DataSnapshot createdLists = user.child("Created lists");
                                            for (DataSnapshot list : createdLists.getChildren()) {
                                                ListAdapter l = list.getValue(ListAdapter.class);
                                                if (l.getId() == keyNumber) {
                                                    l.setList(clickedList);
                                                    l.setName(listName.getText().toString());
                                                    DatabaseReference d = databaseReference.child(user.getKey()).child("Created lists").child(list.getKey());
                                                    d.setValue(l);
                                                }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(UserViewList.this);
                builder.setMessage("Are you sure you want to delete " + clickedItem + " ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot user_lists = uniqueUserSnapshot.child("Created lists");
                    for (DataSnapshot user_list : user_lists.getChildren()) {
                        ListAdapter LA = user_list.getValue(ListAdapter.class);
                        if (LA.getId() == keyNumber) {
                            listName.setText(LA.getName());
                            for (int j = 0; j < LA.getList().size(); j++) {
                                clickedList.add((LA.getList().get(j)));
                            }
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