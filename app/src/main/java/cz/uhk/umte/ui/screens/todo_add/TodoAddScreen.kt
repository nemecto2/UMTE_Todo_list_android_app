package cz.uhk.umte.ui.screens.todo_add

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.func.formatDate
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun TodoAddScreen(
    controller: NavHostController,
    viewModel: TodoAddVM = getViewModel()
) {
    val DEFAULT_DATE_PATTERN: String = "--.--.----"

    var inputText by remember { mutableStateOf("") }
    var selectedDateText by remember { mutableStateOf(DEFAULT_DATE_PATTERN) }
    var noteText by remember { mutableStateOf("") }

    @Composable
    fun DatePicker(): DatePickerDialog {
        // Date picker
        val calendar = Calendar.getInstance()
        val context = LocalContext.current

        // Fetching current year, month and day
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                val insertMonth = if (selectedMonth + 1 > 9) "${selectedMonth + 1}" else "0${selectedMonth + 1}"
                val insertDay = if (selectedDayOfMonth > 9) "$selectedDayOfMonth" else "0$selectedDayOfMonth"
                selectedDateText = "$selectedYear-$insertMonth-$insertDay"
            },
            year,
            month,
            dayOfMonth
        )
        // Disable past dates
        datePicker.datePicker.minDate = calendar.timeInMillis

        return datePicker
    }

    val datePicker = DatePicker()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = {
                    Text(text = "Připomínka")
                },
                modifier = Modifier.weight(1F),
                singleLine = true,
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Button(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(48.dp),
                onClick = {
                    if (inputText.isNotEmpty()) {
                        viewModel.addOrUpdateTodo(
                            TodoEntity(
                                text = inputText,
                                date = if (selectedDateText == DEFAULT_DATE_PATTERN) null else selectedDateText,
                                note = noteText.ifEmpty { null },
                            )
                        )
                        controller.popBackStack()
                    }
                },
            ) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Přidat",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
                )
//                Text(text = "Přidat")
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            Button(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(48.dp),
                onClick = { datePicker.show() },
            ) {
                Image(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Add",
                    modifier = Modifier.size(48.dp),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Text(text = formatDate(selectedDateText))
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = {
                    Text(text = "Připomínka")
                },
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
//                    .fillMaxHeight()
            )
        }
    }
}