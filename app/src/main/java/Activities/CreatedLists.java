package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import Adapters.ListAdapter;

public class CreatedLists extends AppCompatActivity {
    private TextView returnBack, addList;
    private ImageButton Refresh;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Created lists");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_lists);
        setupUI();


        //Creating a Linear view List to be shown in this activity.
        final ListView list = findViewById(R.id.list);
        ArrayList<String> listHistory = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listHistory);
        list.setAdapter(arrayAdapter);

        //When item get clicked, move its key to the next page.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                String sp[] = clickedItem.split("ID:");
                String key = sp[1];
                Intent i = new Intent(CreatedLists.this, ClickedListManager.class);
                i.putExtra("listKey", key);
                startActivity(i);
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

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CreatedLists.this, LoggedInProfile.class));
            }
        });

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CreatedLists.this, addNewList.class));
            }
        });

        //fill the list from the database, with id.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    ListAdapter LA = uniqueUserSnapshot.getValue(ListAdapter.class);
                    listHistory.add(LA.getName() + " ID:" + LA.getId());
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void setupUI() {
        returnBack = (TextView) findViewById(R.id.returnBackKey);
        addList = (TextView) findViewById(R.id.addList);
        Refresh = (ImageButton) findViewById(R.id.refresh);

    }

}