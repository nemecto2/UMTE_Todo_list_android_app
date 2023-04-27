package cz.uhk.umte.ui.notifications


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
//        val message = intent?.getStringArrayExtra("TODOS") ?: return

//        println("Notification test:   $message")
        println("Notification test!")
    }
}