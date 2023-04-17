package cz.uhk.umte.ui.screens.todo_detail

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.uhk.umte.db.entities.NoteEntity
import cz.uhk.umte.db.entities.TodoEntity
import cz.uhk.umte.func.formatDate
import cz.uhk.umte.ui.components.dialogs.DeleteDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.*

@Composable
fun TodoDetailScreen(
    controller: NavHostController,
    todoId: Long,
    viewModel: TodoDetailVM = getViewModel {
        parametersOf(todoId)
    },
) {
    // TODO jak udělat načítání do inputů z DB?
    val todo = viewModel.todo.collectAsState(TodoEntity())
    val noteList = viewModel.notes.collectAsState(emptyList())

    val note by remember { mutableStateOf(if (noteList.value.size == 1) noteList.value[0] else NoteEntity(todoId = todoId))}

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        // TODO co přesně znamená by remember?
        val showDeleteDialog = remember { mutableStateOf(false) }

        var updateText by remember { mutableStateOf(todo.value.text) }
        var updateDate by remember { mutableStateOf(todo.value.date.orEmpty()) }

        var updateNote by remember { mutableStateOf(note.text) }

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
                    updateDate = "$selectedYear-$insertMonth-$insertDay"
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


        // Buttons
        Row(

        ) {
            Button(
                onClick = {
                    if (updateText.isNotEmpty()) {
                        viewModel.addOrUpdate(
                            todoEntity = todo.value,
                            noteEntity = note,
                            todo = updateText,
                            date = if (updateDate == "") null else updateDate,
                            note = updateNote,
                        )
                        controller.popBackStack()
                    }
                },
            ) {
                Image(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Uložit"
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                Text (
                    text = "Uložit"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    showDeleteDialog.value = true
                },
            ) {
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Odstranit"
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                Text (
                    text = "Odstranit"
                )
            }
        }


        // DIVIDER //////////////////////
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))


        if (todo.value != null) {
            // Input ///////////////
            OutlinedTextField(
                value = updateText,
                onValueChange = { updateText = it },
                label = {
                    Text(text = "Připomínka")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // Date /////////////////
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

                Text(text = formatDate(updateDate ?: "--.--.----"))
            }


            // DIVIDER //////////////////////
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))


            // NOTE //////////////////////
            OutlinedTextField(
                value = updateNote,
                onValueChange = { updateNote = it },
                label = {
                    Text(text = "Poznámka")
                },
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
            )
        }

        // DELETE DIALOG ////////////
        DeleteDialog(
            onConfirm = {
                controller.popBackStack()
                viewModel.deleteTodo(todo.value)
                showDeleteDialog.value = false
            },
            onDismiss = {
                showDeleteDialog.value = false
            },
            show = showDeleteDialog.value
        )
    }
}