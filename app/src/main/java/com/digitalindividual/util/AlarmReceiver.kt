package com.digitalindividual.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import android.widget.Toast
import com.digitalindividual.bernadete.R
import android.app.PendingIntent
import com.digitalindividual.bernadete.MainActivity


/**
 * Created by 17170077 on 28/11/2018.
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "my_app"
            val descriptionText = "TextDescriptio"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("my_app", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        var mBuilder = NotificationCompat.Builder(context, "my_app")
                .setSmallIcon(R.drawable.logo_brecho)
                .setContentTitle(intent.getStringExtra("Titulo"))
                .setContentText("Peça de Roupa: ${intent.getStringExtra("Roupa")}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationIntent = Intent(context, MainActivity::class.java)

        notificationIntent.putExtra("id", intent.getIntExtra("id", 0))

        val contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        mBuilder.setContentIntent(contentIntent)

        with(NotificationManagerCompat.from(context)) {
            notify(1, mBuilder.build())
        }

    }

}