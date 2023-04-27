package cz.uhk.umte.ui.screens.todo_list_date

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cz.uhk.umte.func.formatDate
import cz.uhk.umte.ui.navigateTodoDetail
import cz.uhk.umte.ui.components.todo.Todo
import org.koin.androidx.compose.getViewModel

@Composable
fun TodoListDateScreen (
    controller: NavHostController,
    viewModel: TodoListDateVM = getViewModel(),
) {
    // Vytažení všech TODOS z VM
    val todos = viewModel.todos.collectAsState(emptyList())

    viewModel.deleteOldTodos()


    // Sestavení pohledu
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var date = ""

        if (todos.value.isEmpty()) {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(
                text = "Žádná připomínka k zobrazení",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.94f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(
                items = todos.value,
                key = { todo -> todo.id },
            ) { todo ->
                /* Check new date */
                if (todo.date != date) {
                    Divider(
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )

                    Text(
                        text = formatDate(todo.date.orEmpty()),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.secondary
                    )
                }
                date = todo.date.orEmpty()


                /* End of checking new date*/

                Todo(
                    text = todo.text,
                    checked = todo.checked,
                    handleChecked = { viewModel.handleTodoCheck(todo) },
                    handleDetail = { controller.navigateTodoDetail(todo.id) },
                )
            }
        }
    }
}