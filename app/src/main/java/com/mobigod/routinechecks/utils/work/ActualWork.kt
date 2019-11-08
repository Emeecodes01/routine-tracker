package com.mobigod.routinechecks.utils.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobigod.routinechecks.R
import com.mobigod.routinechecks.data.Repository
import com.mobigod.routinechecks.utils.recievers.RoutineDoneBoardcastReceiver

class ActualWork constructor(var repository: Repository,
                             var context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {


    override fun doWork(): Result {
        //for now

        //increase the count of Routine
        repository.increaseRoutineCount()

        val title = inputData.getString("title")
        //val id = inputData.getString(title, "")
        val contentText = "This is to remind you of $title in the next 5 mins"

        //Create a  intent to start a broadcast reciever to handle checking a work as done
        val routineIntent = Intent(context, RoutineDoneBoardcastReceiver::class.java).apply {
            putExtra("title", title)
        }

        //Pending intent to start thne boardcast reciever
        val routinePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, routineIntent, 0)

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Reminder")
            .setAutoCancel(true)
            .setContentText(contentText)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_done_all_black_24dp, "Done", routinePendingIntent)

        createNotificationChannel("notification_ch_2", contentText)

        with(NotificationManagerCompat.from(context)){
            notify(NOTIFICATION_ID, builder.build())
        }

        return  Result.success()
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "not_2"
        const val NOTIFICATION_ID = 2
        const val NOTIFICATION_INTENT_ID = "not_int"
    }


    private fun createNotificationChannel(name: String, descriptionText: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(ReminderWork.NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }


}