package com.example.notificationprocedures

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val messagetext = intent?.getStringExtra("toast")

        Toast.makeText(context,messagetext,Toast.LENGTH_LONG).show()
    }
}