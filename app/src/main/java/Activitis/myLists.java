package Activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class myLists extends AppCompatActivity {
    private TextView returnBack, addList;

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
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(myLists.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });

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