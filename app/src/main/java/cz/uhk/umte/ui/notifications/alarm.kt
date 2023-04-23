//package cz.uhk.umte.ui.notifications
//
//import android.annotation.SuppressLint
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import java.time.LocalDateTime
//import java.util.*
//
//
//@SuppressLint("UnspecifiedImmutableFlag")
//fun setAlarm(context: Context) {
//    val calendar = Calendar.getInstance()
//
//    val current = LocalDateTime.of(
//        year = calendar.get(Calendar.YEAR),
//        month = calendar.get(Calendar.MONTH),
//        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH),
//        hour = calendar.get(6),
//        minute = calendar.get(0),
//        second = calendar.get(0)
//    )
//
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
////    val intent = Intent(context, Alarm::class.java)
////    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
////    alarmManager.set(AlarmManager.RTC, current, pendingIntent)
////    alarmManager.setInexactRepeating(AlarmManager.RTC, current, AlarmManager.INTERVAL_DAY, pendingIntent)
//}