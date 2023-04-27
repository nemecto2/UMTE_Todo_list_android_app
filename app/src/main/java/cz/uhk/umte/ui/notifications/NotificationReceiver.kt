package cz.uhk.umte.ui.notifications


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cz.uhk.umte.db.dao.TodoDao
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject


class NotificationReceiver : BroadcastReceiver() {
    private val todoDao: TodoDao by inject(TodoDao::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        println("TU")
        if (context == null) { return }

        runBlocking {
            launch {
                todoDao.selectToday().collect { todos ->
                    println(todos)
                    sendPushNotification(context, todos)
                }
            }
        }
    }
}