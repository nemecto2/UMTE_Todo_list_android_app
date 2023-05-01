package cz.uhk.umte.ui.screens.settings

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import cz.uhk.umte.ui.notifications.*
import kotlinx.coroutines.flow.first
import org.koin.androidx.compose.getViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsVM = getViewModel(),
) {
    val context = LocalContext.current

    // Value for storing time as a string
    var notificationTime by remember { mutableStateOf("--:--") }

    LaunchedEffect(key1 = Unit, block = {
        notificationTime = viewModel.notificationTimeFlow.first().orEmpty()
        if (notificationTime == "") {
            notificationTime = "--:--"
        }
    })



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
                notificationTime = "$mHour:${if (mMinute < 10) "0" else ""}$mMinute"
                viewModel.setNotificationTime(notificationTime)

                NotificationManager.schedule(
                    context,
                    context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager,
                    NotificationManager.getTimeInMillis(mHour, mMinute),
                )

                Toast.makeText(context, "Notifikace nastaveny", Toast.LENGTH_SHORT).show()

            }, mHour, mMinute, true
        )

        return mTimePickerDialog
    }

    val timePickerDialog = getTimePickerDialog()


    // Vytažení všech TODOS z VM
    val todos = viewModel.todos.collectAsState(emptyList())



    // Sestavení pohledu
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // SETTINGS /////////////////////////
        Button (
            onClick = {
                if (!notificationsPermissionState.status.isGranted) {
                    notificationsPermissionState.launchPermissionRequest()
                }

                if (!alarmPermissionState.status.isGranted) {
                    alarmPermissionState.launchPermissionRequest()
                }

                if (!scheduleAlarmPermissionState.status.isGranted) {
                    alarmPermissionState.launchPermissionRequest()
                }

                timePickerDialog.show()

                context.packageManager.setComponentEnabledSetting(
                    ComponentName(context, NotificationReceiver::class.java),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )

            },
            content = {
                Text(text = "Nastavit upozornění")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = notificationTime,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button (
            onClick = {
                NotificationManager.cancel(
                    context,
                    context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager,
                )

                context.packageManager.setComponentEnabledSetting(
                    ComponentName(context, NotificationReceiver::class.java),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )

                notificationTime = "--:--"
                viewModel.setNotificationTime(notificationTime)

                Toast.makeText(context, "Notifikace zrušeny", Toast.LENGTH_SHORT).show()
            },
            content = {
                Text(text = "Zrušit upozornění")
            }
        )

        // DIVIDER /////////////////////////////////
        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // SEND NOTIFICATION ///////////////////////
        Button(
            onClick = {
                sendPushNotification(context, todos.value)
            },
            content = {
                Text(text = "Odeslat notifikaci")
            }
        )

    }
}