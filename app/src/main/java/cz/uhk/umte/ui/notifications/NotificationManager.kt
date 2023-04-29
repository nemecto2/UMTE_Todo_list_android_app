package cz.uhk.umte.ui.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import java.util.*

class NotificationManager() {
    companion object {
        private val REQUEST_CODE: Int = 51312

        fun schedule(context: Context, alarmManager: AlarmManager?) {
            // Set the alarm to start at 8:30 a.m.
//        val calendar: Calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, 8)
//            set(Calendar.MINUTE, 30)
//        }
            val calendar: Calendar = Calendar.getInstance()
            println(calendar.time)
            calendar.add(Calendar.SECOND, 15)
            println(calendar.time)


            val intent = Intent(context, NotificationReceiver::class.java)
//                          .apply {
//                              putExtra("TODOS", "message")
//                              println("Notification:   calendar")
//                          }

            val pendingIntent =  PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }


        fun cancel(context: Context, alarmManager: AlarmManager) {
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    REQUEST_CODE,
                    Intent(context, NotificationReceiver::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }
}