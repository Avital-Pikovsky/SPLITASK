package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class EditListByManager extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemLongClickListener {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Created lists");
    private ArrayList<String> clickedList;

    private EditText listName;
    private Button invite, Add, ChangeName;
    private Dialog dialog;
    private MyAdapter adapter;
    private int keyNumber = 0;

    //This Activity is a dynamic activity.
    //It contains a List, and fill it by the current list that have been clicked in the previous page.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_by_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setupUI();

        String key = getIntent().getExtras().getString("listKey");
        key.replaceAll("\\s", "");
        keyNumber = Integer.parseInt(key);
        adapter = new MyAdapter();

        final ListView list = findViewById(R.id.listClicked);
        clickedList = new ArrayList<>();
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) list.getItemAtPosition(position);


            }
        });


//        Listener to the 'JOIN' button, open a dialog when clicked
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditListByManager.this);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.findViewById(R.id.button_cancel).setOnClickListener(
                        EditListByManager.this);
                dialog.findViewById(R.id.button_ok).setOnClickListener(
                        EditListByManager.this);
                dialog.show();

            }
        });

        ChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListDB();
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
                        for (int j = 0; j < LA.getList().size(); j++) {
                            clickedList.add((LA.getList().get(j)));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                    if (clickedList.size() == 1 && clickedList.get(0).equals("The list is empty")) {
                        clickedList.add(text);
                        clickedList.remove("The list is empty");
                    }
                    else{
                        clickedList.add(text);
                    }
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                        updateListDB();

                }
                break;
        }
    }

    public void updateListDB() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot list : snapshot.getChildren()) {
                    ListAdapter l = list.getValue(ListAdapter.class);
                    if (l.getId() == keyNumber) {
                        l.setList(clickedList);
                        l.setName(listName.getText().toString());
                        DatabaseReference d = databaseReference.child(list.getKey());
                        d.setValue(l);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> listView, View view,
                                   int position, long column) {
        if(clickedList.size() == 1) {
            clickedList.add("The list is empty");
        }
        clickedList.remove(position);
        adapter.notifyDataSetChanged();

        updateListDB();

        return true;
    }

    //Adapter for the list
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return clickedList.size();
        }

        @Override
        public Object getItem(int position) {
            return clickedList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) convertView;
            if (null == view) {
                view = new TextView(EditListByManager.this);
                view.setPadding(10, 10, 10, 10);
            }
            view.setText(clickedList.get(position));
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

    private void setupUI() {
        listName = (EditText) findViewById(R.id.freindListName);
        invite = (Button) findViewById(R.id.invites);
        Add = (Button) findViewById(R.id.edit);
        ChangeName = (Button) findViewById(R.id.changeN);
    }
}