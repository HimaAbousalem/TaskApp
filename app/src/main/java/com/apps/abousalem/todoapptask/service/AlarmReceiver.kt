package com.apps.abousalem.todoapptask.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.apps.abousalem.todoapptask.R

class AlarmReceiver: BroadcastReceiver(){
    private val CHANNEL_ID = "Tasks"
    private val CHANNEL_NAME = "name"
    private var unDoneTasks: Int? = null
    private var mManager: NotificationManager? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        unDoneTasks = intent!!.getIntExtra("unDoneTasks",0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context!!)
        }
        val notification =  createNotification(context!!)
        getManager(context).notify(1,notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lightColor = R.color.colorPrimary
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager(context).createNotificationChannel(channel)
    }

    private fun getManager(context: Context): NotificationManager{
        if(mManager == null){
            mManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return mManager!!
    }

    private fun createNotification(context: Context): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Task Manager")
            .setContentText("REMINDER: You still have $unDoneTasks uncompleted Tasks.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}
