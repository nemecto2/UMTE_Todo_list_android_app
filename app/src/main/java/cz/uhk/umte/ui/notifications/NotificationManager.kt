package cz.uhk.umte.ui.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import java.util.*

class NotificationManager() {
    companion object {
        private val REQUEST_CODE: Int = 51312

        fun getTimeInMillis(hour: Int, minute: Int): Long {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            return calendar.timeInMillis
        }

        fun schedule(context: Context, alarmManager: AlarmManager?, timeInMillis: Long, intent: Intent? = null) {
            // Set the alarm to start at given time
            val intentLocal = intent ?: Intent(context, NotificationReceiver::class.java)

            val pendingIntent =  PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intentLocal,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


//            alarmManager?.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                timeInMillis,
//                AlarmManager.INTERVAL_DAY,
//                pendingIntent
//            )
            alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }


        fun cancel(context: Context, alarmManager: AlarmManager?) {
            alarmManager?.cancel(
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