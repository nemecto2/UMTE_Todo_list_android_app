package cz.uhk.umte.ui.notifications


import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cz.uhk.umte.db.dao.TodoDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject
import java.util.*


class NotificationReceiver : BroadcastReceiver() {
    private val todoDao: TodoDao by inject(TodoDao::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        val timeInMillis: Long = Calendar.getInstance().timeInMillis + 86400000

        if (context == null) { return }

        if (context.isNotificationGranted()) {
            runBlocking {
                launch {
                    todoDao.selectToday().collect { todos ->
                        println(todos)
                        sendPushNotification(context, todos)
                    }
                }
            }

            NotificationManager.schedule(
                context,
                context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager,
                timeInMillis
            )
        }
    }
}