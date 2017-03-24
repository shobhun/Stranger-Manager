package com.example.admin.strangermanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user = sharedPreferences.getString("User","NOUSER");
        if (user.equals("NOUSER"))
        {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,new fragment_login()).commit();
        }
        else
        {
            Intent ContactListActivity = new Intent(this, ContactListActivity.class);
            startActivity(ContactListActivity);
        }
    }
}
