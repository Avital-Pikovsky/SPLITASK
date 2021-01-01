package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Adapters.ListAdapter;


public class addNewList extends AppCompatActivity implements OnClickListener,
        OnItemLongClickListener {

    private ArrayList<String> datasource;
    private MyAdapter adapter;
    private Dialog dialog;
    private EditText listName;
    private Button save;

    //Auto functions
    private FirebaseAuth firebaseAuth;

    //The data base itself
    private FirebaseDatabase firebaseDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setupUI();

        //Connecting akk the buttons to the xml by id
        setupUI();

        datasource = new ArrayList<String>();
        adapter = new MyAdapter();


        //creating a list with Adapter
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);

        //Listener to the 'JOIN' button, open a dialog when clicked
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(addNewList.this);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.findViewById(R.id.button_cancel).setOnClickListener(
                        addNewList.this);
                dialog.findViewById(R.id.button_ok).setOnClickListener(
                        addNewList.this);
                dialog.show();
            }
        });

        //saving a list and add it to the database.
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListToDB(listName.getText().toString(), datasource);
                finish();
                startActivity(new Intent(addNewList.this, CreatedLists.class));
            }

        });
    }



    //this functions adds the new added list to the database,
    //under the person who created it.
    public void addListToDB(String name, ArrayList<String> addedList) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        int id = (int) (Math.random() * (10000 - 1)) + 1;
        ListAdapter newList = new ListAdapter(name, addedList, firebaseAuth.getUid(), id);
        DatabaseReference newListRef = databaseReference.child("Created lists").push();
        newListRef.setValue(newList);

    }

    //the dialog options, get the input from the user.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel:
                dialog.dismiss();
                break;

            case R.id.button_ok:
                String text = ((EditText) dialog.findViewById(R.id.edit_box))
                        .getText().toString();
                if (null != text && 0 != text.compareTo("")) {
                    datasource.add(text);
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }


    private void setupUI() {
        listName = (EditText) findViewById(R.id.listname);
        save = (Button) findViewById(R.id.SaveButton);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> listView, View view,
                                   int position, long column) {
        datasource.remove(position);
        adapter.notifyDataSetChanged();
        return true;
    }
//Adapter for the list
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datasource.size();
        }

        @Override
        public Object getItem(int position) {
            return datasource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) convertView;
            if (null == view) {
                view = new TextView(addNewList.this);
                view.setPadding(10, 10, 10, 10);
            }
            view.setText(datasource.get(position));
            return view;
        }
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





