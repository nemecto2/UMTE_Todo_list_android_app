package cz.uhk.umte.ui.screens.note_list

import android.Manifest
import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import cz.uhk.umte.ui.notifications.*
import org.koin.androidx.compose.getViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoteListScreen(
    controller: NavHostController,
    viewModel: NoteListVM = getViewModel(),
) {
    val context = LocalContext.current

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }


    // Notifications
    val notificationsPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS
    )
    val alarmPermissionState = rememberPermissionState(
        android.Manifest.permission.USE_EXACT_ALARM
    )
    val scheduleAlarmPermissionState = rememberPermissionState(
        android.Manifest.permission.SCHEDULE_EXACT_ALARM
    )

    fun getTimePickerDialog(): TimePickerDialog {
        // Declaring and initializing a calendar
        val mCalendar = Calendar.getInstance()
        val mHour = mCalendar[Calendar.HOUR_OF_DAY]
        val mMinute = mCalendar[Calendar.MINUTE]

        // Creating a TimePicker dialod
        val mTimePickerDialog = TimePickerDialog(
            context,
            {_, mHour : Int, mMinute: Int ->
                mTime.value = "$mHour:$mMinute"
            }, mHour, mMinute, false
        )

        return mTimePickerDialog
    }

    val timePickerDialog = getTimePickerDialog()


    // Vytažení všech TODOS z VM
    val todos = viewModel.todos.collectAsState(emptyList())



    // Sestavení pohledu
    Column {
        // Permissions
//        val permissionGranted = remember {
//            mutableStateOf(context.isNotificationGranted())
//        }
//
//        val launcher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.RequestPermission(),
//            onResult = { granted ->
//                if (granted) {
//                    Toast.makeText(context, "Notifikace povoleny", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Notifikace zamítnuty", Toast.LENGTH_SHORT).show()
//                }
//                permissionGranted.value = granted
//            },
//        )
//
//        Button(
//            onClick = {
//                checkAndRequestPermission(context, launcher)
//            },
//            content = {
//                Text(text = "PERMISSION")
//            }
//        )

        if (!notificationsPermissionState.status.isGranted) {
            Button(
                onClick = {
                    notificationsPermissionState.launchPermissionRequest()
                },
                content = {
                    Text(text = "NOTIFICATION PERMISSION")
                }
            )
        }

        if (!alarmPermissionState.status.isGranted) {
            Button(
                onClick = {
                    alarmPermissionState.launchPermissionRequest()
                },
                content = {
                    Text(text = "ALARM PERMISSION")
                }
            )
        }

        if (!scheduleAlarmPermissionState.status.isGranted) {
            Button(
                onClick = {
                    scheduleAlarmPermissionState.launchPermissionRequest()
                },
                content = {
                    Text(text = "SCHEDULE ALARM PERMISSION")
                }
            )
        }

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

        Button (
            onClick = {
                timePickerDialog.show()
            },
            content = {
                Text(text = "TIME DIALOG ${mTime.value}")
            }
        )
    }
}