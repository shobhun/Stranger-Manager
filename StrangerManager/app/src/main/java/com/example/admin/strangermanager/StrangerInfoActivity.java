package com.example.admin.strangermanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StrangerInfoActivity extends AppCompatActivity
{
    private EditText etxStrangerName,etxPhone,etxMeet,etxPersonality;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stranger_info);

        etxStrangerName = (EditText) findViewById(R.id.etxStrangerName);
        etxMeet = (EditText) findViewById(R.id.etxMeet);
        etxPersonality = (EditText) findViewById(R.id.etxPersonality);
        etxPhone = (EditText) findViewById(R.id.etxPhone);

        Intent intent = getIntent();
        final String signedUserName = intent.getStringExtra("UN");

        findViewById(R.id.btnCreateStranger).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("User Info");

                //getting signed username.To add contacts particularly
                String key = "User-" + signedUserName;
                DatabaseReference signedDataReference = databaseReference.child(key);

                DatabaseReference contactsdataReference = signedDataReference.child("Stranger Contacts");

                String strangerName = etxStrangerName.getText().toString();
                String strangerMeet = etxMeet.getText().toString();
                String strangerPhone = etxPhone.getText().toString();
                String strangerPersonality = etxPersonality.getText().toString();

                String strangerKey = strangerName + " : " + strangerPhone;
                DatabaseReference strangerKeydataRef = contactsdataReference.child(strangerKey);

                strangerKeydataRef.child("Stranger Name: ").setValue(strangerName);
                strangerKeydataRef.child("Stranger Phone: ").setValue(strangerPhone);
                strangerKeydataRef.child("Stranger Personality: ").setValue(strangerPersonality);
                strangerKeydataRef.child("Stranger Meeting Place: ").setValue(strangerMeet);

                Toast.makeText(StrangerInfoActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                StrangerInfoActivity.super.onBackPressed();
            }
        });
    }
}
