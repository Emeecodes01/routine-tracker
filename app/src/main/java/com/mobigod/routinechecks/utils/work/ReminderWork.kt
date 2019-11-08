package com.mobigod.routinechecks.utils.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.RxWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobigod.routinechecks.R
import io.reactivex.Single

/**
 * This class is basically to create reminder of 5 mins b4 the actual work starts
 * */
class ReminderWork(val context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "not_1"
        const val NOTIFICATION_ID = 1
    }

    override fun doWork(): Result {
        //The work here is basically to show a notification to remind the user

        val title = inputData.getString("title")
        val id = inputData.getInt("id", 0)
        val contentText = "This is to remind you of $title in the next 5 mins"

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Reminder")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel("notification_ch_1", contentText)

        with(NotificationManagerCompat.from(context)){
            notify(NOTIFICATION_ID, builder.build())
        }

        return Result.success()

    }


    private fun createNotificationChannel(name: String, descriptionText: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }


}