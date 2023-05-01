package cz.uhk.umte.ui.screens.todo_detail

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
    val context = LocalContext.current

    val todo = viewModel.todo.collectAsState(TodoEntity())
//    val note = viewModel.note.collectAsState(NoteEntity())

    val showDeleteDialog = remember { mutableStateOf(false) }

    if (todo.value != null && todo.value.text.isEmpty()) {
        // nic
    } else {

        var updateText by remember { mutableStateOf(todo.value.text) }
        var updateDate by remember { mutableStateOf(todo.value.date.orEmpty()) }
        var updateImageUri by remember { mutableStateOf<Uri?>(if (todo.value.imageUri == null) null else Uri.parse(todo.value.imageUri)) }
        var updateNote by remember { mutableStateOf(todo.value.note) }
    //    var updateNote by remember { mutableStateOf(note.value.text) }

        @Composable
        fun DatePicker(): DatePickerDialog {
            // Date picker
            val calendar = Calendar.getInstance()

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


        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.94f)
                .verticalScroll(rememberScrollState()),

        ) {
            // Buttons
            Row(

            ) {
                Button(
                    onClick = {
                        if (updateText.isNotEmpty()) {
                            var imageUri: String? = updateImageUri.toString()
                            if (imageUri == "null") {
                                imageUri = null
                            }

                            viewModel.addOrUpdate(
                                todoEntity = todo.value,
    //                            noteEntity = note.value,
                                todo = updateText,
                                date = if (updateDate == "") null else updateDate,
                                note = updateNote,
                                imageUri = imageUri,
                            )

                            Toast.makeText(context, "Připomínka uložena", Toast.LENGTH_SHORT).show()

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
                    Text(
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
                    Text(
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
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    Text(text = formatDate(updateDate ?: "--.--.----"))
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // NOTE //////////////////////
                OutlinedTextField(
                    value = updateNote.orEmpty(),
                    onValueChange = { updateNote = it },
                    label = {
                        Text(text = "Poznámka")
                    },
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                )


                // DIVIDER //////////////////////
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))


                // Image /////////////////
                val bitmap = remember { mutableStateOf<Bitmap?>(null) }

                var launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                        updateImageUri = uri
                    }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { launcher.launch("image/*") }
                    ) {
                        Image(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Přidat nebo upravit obrázek"
                        )
                        Spacer(
                            modifier = Modifier.width(16.dp)
                        )

                        if (updateImageUri == null) {
                            Text(
                                text = "Přidat obrázek"
                            )
                        } else {
                            Text(
                                text = "Upravit obrázek"
                            )
                        }
                    }

    //                Text(text = updateImageUri.toString())
                }

                updateImageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore
                            .Images
                            .Media
                            .getBitmap(context.contentResolver, updateImageUri)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

    //                val byteArrayOutputStream = ByteArrayOutputStream()
    //                bitmap.value?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    //                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    //                updateImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }


                bitmap.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(400.dp).padding(20.dp)
                    )
                }

    //            var r = updateImageUri?.toString()?.replace("content://","")?.let { File(it) }
    //            println(r)
    //
    //            AsyncImage(
    //                model = File(r),
    //                contentDescription = null
    //            )


    //            // DIVIDER //////////////////////
    //            Spacer(modifier = Modifier.height(16.dp))
    //            Divider(
    //                color = MaterialTheme.colors.secondary,
    //                modifier = Modifier
    //                    .fillMaxWidth()
    //                    .height(1.dp)
    //            )
    //            Spacer(modifier = Modifier.height(8.dp))

            }

            // DELETE DIALOG ////////////
            DeleteDialog(
                onConfirm = {
                    viewModel.deleteTodo(todo.value)
                    showDeleteDialog.value = false
                    Toast.makeText(context, "Připomínka odstraněna", Toast.LENGTH_SHORT).show()
                    controller.popBackStack()
                },
                onDismiss = {
                    showDeleteDialog.value = false
                },
                show = showDeleteDialog.value
            )
        }
    }
}