package cz.uhk.umte.ui.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import cz.uhk.umte.R
import cz.uhk.umte.db.dao.TodoDao
import cz.uhk.umte.db.entities.TodoEntity


const val NOTIFICATION_CHANNEL_ID: String = "General"
const val NOTIFICATION_GROUP_ID: String = "today_todos"


@Composable
fun NotificationScreen(
    todoDao: TodoDao
) {
    val todos = todoDao.selectToday().collectAsState(emptyList())

    val context = LocalContext.current

    val permissionGranted = remember {
        mutableStateOf(context.isNotificationGranted())
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                Toast.makeText(context, "Povoleno", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Zamítnuto", Toast.LENGTH_SHORT).show()
            }
            permissionGranted.value = granted
        },
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Button(onClick = { checkAndRequestPermission(context, launcher) }) {
            Text(text = "Získat oprávnění")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Oprávnění získáno: ${permissionGranted.value}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { sendPushNotification(context, todos.value) }) {
            Text(text = "Odeslat notifikaci")
        }
    }
}




fun createTodaysTodoNotification(context: Context, todo: String): Notification {
    val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(todo)
//        .setContentText("Toto je tělo notifikace")
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setGroup(NOTIFICATION_GROUP_ID)
    return builder.build()
}

fun sendPushNotification(context: Context, todos: List<TodoEntity>) {

    val notifications: MutableList<Notification> = mutableListOf()

    for (todo in todos) {
        notifications.add(createTodaysTodoNotification(context, todo.text))
    }

    val summaryNotification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle("Tvoje dnešní nálož připomínek!")
        // Set content text to support devices running API level < 24.
        .setContentText("${todos.size} připomínek")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        // Build summary info into InboxStyle template.
//        .setStyle(NotificationCompat.InboxStyle()
//            .addLine("Alex Faarborg Check this out")
//            .addLine("Jeff Chang Launch Party")
//            .setBigContentTitle("2 new messages")
//            .setSummaryText("janedoe@example.com"))
        // Specify which group this notification belongs to.
        .setGroup(NOTIFICATION_GROUP_ID)
        // Set this notification as the summary for the group.
        .setGroupSummary(true)
        .build()


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Default channel"
        val description = "Kanál pro zobrazování obecných informací."
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = description
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    NotificationManagerCompat.from(context).apply {
        for ((i, notification) in notifications.withIndex()) {
            notify(i, notification)
        }
        notify(-1, summaryNotification)
    }
}

fun checkAndRequestPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
) {
    val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
    } else {
        PackageManager.PERMISSION_GRANTED
    }
    if (granted == PackageManager.PERMISSION_GRANTED) {
        // Povolení je uděleno
    } else {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

private fun Context.isNotificationGranted() =
    ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED
