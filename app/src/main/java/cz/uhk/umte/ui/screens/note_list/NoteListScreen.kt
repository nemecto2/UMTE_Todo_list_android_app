package cz.uhk.umte.ui.screens.note_list

import android.app.AlarmManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.uhk.umte.ui.notifications.*
import org.koin.androidx.compose.getViewModel

@Composable
fun NoteListScreen(
    controller: NavHostController,
//    notificationManager: NotificationManager,
    viewModel: NoteListVM = getViewModel(),
) {
    val context = LocalContext.current

    // Vytažení všech TODOS z VM
    val notes = viewModel.notes.collectAsState(emptyList())
    val todos = viewModel.todos.collectAsState(emptyList())

    // Sestavení pohledu
    Column {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1F),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            contentPadding = PaddingValues(16.dp),
//        ) {
//            items(
//                items = notes.value,
//                key = { note -> note.id },
//            ) { note ->
//                Text(
//                   text = note.text
//                )
//
//                Text(
//                    text = "${note.todoId}",
//                    color = MaterialTheme.colors.secondary,
//                )
//
//                Divider(
//                    color = MaterialTheme.colors.secondary,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(1.dp)
//                )
//            }
//        }

//        NotificationScreen(viewModel.todoDao)


        // Permissions
        val permissionGranted = remember {
            mutableStateOf(context.isNotificationGranted())
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                if (granted) {
                    Toast.makeText(context, "Notifikace povoleny", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Notifikace zamítnuty", Toast.LENGTH_SHORT).show()
                }
                permissionGranted.value = granted
            },
        )

        Button(
            onClick = {
                checkAndRequestPermission(context, launcher)
            },
            content = {
                Text(text = "PERMISSION")
            }
        )

        Button(
            onClick = {
//                notificationManager.schedule()
                NotificationManager.schedule(context, context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)
            },
            content = {
                Text(text = "SCHEDULE")
            }
        )

        Button(
            onClick = {
//                notificationManager.schedule()
                NotificationManager.cancel(context, context.getSystemService(Context.ALARM_SERVICE) as AlarmManager)
            },
            content = {
                Text(text = "CANCEL")
            }
        )

        Button(
            onClick = {
                sendPushNotification(context, todos.value)
            },
            content = {
                Text(text = "SEND NOTIFICATIONS")
            }
        )
    }
}