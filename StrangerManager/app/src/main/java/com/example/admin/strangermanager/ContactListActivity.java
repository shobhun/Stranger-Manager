package com.example.admin.strangermanager;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;

import java.security.Permission;
import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<Object> arrayList = new ArrayList<Object>();
    private TextView logOut;
    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;
    private FirebaseAuth fbAuth;
    private Merlin merlin;

//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        merlin.bind();
//    }
//
//    @Override
//    protected void onPause()
//    {
//        merlin.unbind();
//        super.onPause();
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.READ_PHONE_STATE};
            int flag = ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE);
            if (flag == PackageManager.PERMISSION_DENIED)
            {
                this.requestPermissions(permission,1001);
            }
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String User = sharedPreferences.getString("User","NOUSER");


        listView = (ListView) findViewById(R.id.listView);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User Info");

        final String[] userSplit = User.split("@");
        String key = "User-" + userSplit[0];
        DatabaseReference keyDBR = databaseReference.child(key);

        DatabaseReference StrangerContactsDBR = keyDBR.child("Stranger Contacts");

        final ArrayList<String> arrayLisT = new ArrayList();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                arrayLisT);

        StrangerContactsDBR.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                String child = dataSnapshot.getKey();
                arrayLisT.add(child);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        listView.setAdapter(arrayAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ContactListActivity.this,StrangerInfoActivity.class);
                intent.putExtra("UN" , userSplit[0]);
                startActivity(intent);
            }
        });
//        merlin = new Merlin.Builder().withConnectableCallbacks().build(this);
//        Merlin merlin1 = new Merlin.Builder().withAllCallbacks().build();
//
//
//        merlin.registerConnectable(new Connectable() {
//            @Override
//            public void onConnect()
//            {
//                // Do something you haz internet!
//                Toast.makeText(ContactListActivity.this, "Connected to internet", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        merlin.registerDisconnectable(new Disconnectable()
//        {
//
//            @Override
//            public void onDisconnect()
//            {
//                Toast.makeText(ContactListActivity.this, "No internet", Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_drawer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.LogOut:
            {
                sharedPreferences.edit().clear().apply();
                Toast.makeText(ContactListActivity.this, "Logged Out Successfully.", Toast.LENGTH_SHORT).show();
                finish();
                Intent mainActivity = new Intent(ContactListActivity.this,MainActivity.class);
                startActivity(mainActivity);
            }
            case R.id.ChangeEmail:
//            {
//                FirebaseUser firebaseUser = fbAuth.getCurrentUser();
//                firebaseUser.updateEmail();
//            }

            case R.id.ChangePassword:

            case R.id.AboutUs:



        }
        return super.onOptionsItemSelected(item);
    }
}
