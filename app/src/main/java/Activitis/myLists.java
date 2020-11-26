package Activitis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class myLists extends AppCompatActivity {
    private TextView returnBack, addList;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("User Lists");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);
        setupUI();

        final ListView list = findViewById(R.id.list);
        ArrayList<String> listHistory = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listHistory);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);
                Toast.makeText(myLists.this, clickedItem, Toast.LENGTH_LONG).show();
            }
        });
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    ListAdapter LA = snapshot.getValue(ListAdapter.class);
//                    listHistory.add(LA.getName());
//                }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(myLists.this, error.getCode(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(myLists.this, LoggedInProfile.class));
            }
        });

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(myLists.this, addNewList.class));
            }
        });


    }

    private void setupUI() {
        returnBack = (TextView) findViewById(R.id.returnBackKey);
        addList = (TextView) findViewById(R.id.addList);


    }

}