package com.example.admin.strangermanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Admin on 03-12-2016.
 */

public class ReceiverCall extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            Toast.makeText(context, "Stranger Rceiver Started", Toast.LENGTH_SHORT).show();

            String incomingNum = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Intent DialogIntent = new Intent(context.getApplicationContext(), DialogActivity.class);
            DialogIntent.putExtra("num", incomingNum);
            DialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(DialogIntent);

        }
    }
}
