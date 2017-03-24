package com.example.admin.strangermanager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogActivity extends AppCompatActivity
{
    private ListView listView1;
    private String UserName, phoneNumber,data = "UNKNOWN" , data1 = " ";
    private Snackbar snackbar;
    private SharedPreferences sharedPreferences;
    private PopupWindow popupDialog;
    private Button btnOk,btnInside,customButtonYes;
    private TextView txtStrangerInfo,txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserName = sharedPreferences.getString("User","NOUSER");
        String[] UserNameSplit = UserName.split("@");

        //Getting mob no from receiver
        Intent intent = getIntent();
        final String phone = intent.getStringExtra("num");
        int startingPoint = phone.length() - 10;
        phoneNumber = phone.substring(startingPoint);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User Info");



        String key = "User-" + UserNameSplit[0];
        DatabaseReference keyDBR = databaseReference.child(key);
        DatabaseReference StrangerContactsDBR = keyDBR.child("Stranger Contacts");
        StrangerContactsDBR.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                data = dataSnapshot.getKey();
                String number = data.substring(data.length() - 10);
                boolean flag = phoneNumber.equals(number);

                if (flag)
                {
                    data1 = dataSnapshot.getValue().toString();
                    Log.d("TAG:",data);

                    data1 = data1.substring(1,data1.length()-1);
                    data1 = data1.replace("Stranger","");
                    String[] info = data1.split(",");

                    Dialog dialog=new Dialog(DialogActivity.this);
                    dialog.setContentView(R.layout.activity_dialog);

                    txtStrangerInfo = (TextView) dialog.findViewById(R.id.txtStrangerInfo);
                    txtUser = (TextView) dialog.findViewById(R.id.txtCaller);
                    txtStrangerInfo.setText("\n "+info[0]+"\n"+info[1]+"\n"+info[2]);
                    txtUser.setText(data);
                    customButtonYes=(Button) dialog.findViewById(R.id.customButtonYes);

                    customButtonYes.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            finish();
                        }
                    });
                    dialog.show();

                }
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

    }
}
