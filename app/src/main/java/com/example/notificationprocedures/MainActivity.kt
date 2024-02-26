package com.example.notificationprocedures

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationprocedures.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID ="1"

    lateinit var mainBinding: ActivityMainBinding
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.sendNotification.setOnClickListener {

            counter++
            mainBinding.sendNotification.text = counter.toString()
            if (counter % 5 == 0){
                startNotification()
            }

        }
    }

    fun startNotification()
    {
        val intent = Intent(applicationContext,MainActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= 23){
            PendingIntent.getActivity(applicationContext,0,intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }else {
            PendingIntent.getActivity(applicationContext,0,intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //actionButton
        val actionIntent = Intent(applicationContext,Receiver::class.java)
        actionIntent.putExtra("toast","This is a notification message")

        val actionPending = if (Build.VERSION.SDK_INT >= 23){
            PendingIntent.getBroadcast(applicationContext,1,actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }else {
            PendingIntent.getBroadcast(applicationContext,1,actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //dismissButton
        val dismissIntent = Intent(applicationContext,ReceiverDismiss::class.java)
        actionIntent.putExtra("toast","This is a notification message")

        val dismissPending = if (Build.VERSION.SDK_INT >= 23){
            PendingIntent.getBroadcast(applicationContext,2,dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }else {
            PendingIntent.getBroadcast(applicationContext,2,dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val icon : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.whatsapp)
        val text : String = resources.getString(R.string.big_text)

        val builder = NotificationCompat.Builder(this@MainActivity,CHANNEL_ID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,"1",NotificationManager.IMPORTANCE_DEFAULT)
            val manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            builder.setSmallIcon(R.drawable.small_icon)
                .setContentTitle("Title")
                .setContentText("Notification Text")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.small_icon,"Toast Message",actionPending)
                .addAction(R.drawable.small_icon,"Dismiss",dismissPending)
                .setLargeIcon(icon)
//                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
        }
        else{
            builder.setSmallIcon(R.drawable.small_icon)
                .setContentTitle("Notification Title")
                .setContentText("This is the notification text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.small_icon,"Toast Message",actionPending)
                .addAction(R.drawable.small_icon,"Dismiss",dismissPending)
                .setLargeIcon(icon)
//                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon))
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
        }

        val notificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)
        notificationManagerCompat.notify(1,builder.build())
    }
}