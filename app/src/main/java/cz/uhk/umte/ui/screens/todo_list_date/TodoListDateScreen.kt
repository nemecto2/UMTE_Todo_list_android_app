package cz.uhk.umte.ui.screens.todo_list_date

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cz.uhk.umte.db.entities.TodoEntity
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
    val todosFromVM = viewModel.todos.collectAsState(emptyList())

    // Sestavení pohledu
    Column {
        val splitTodos: MutableList<MutableList<TodoEntity>> = mutableListOf()

        var date = ""
        var i = -1
        for (todo in todosFromVM.value) {
            if (!todo.date.equals(date)) {
                splitTodos.add(mutableListOf())
                date = todo.date.orEmpty()
                i++
            }

            splitTodos[i].add(todo)
        }


        if (todosFromVM.value.isEmpty()) {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(
                text = "Žádná připomínka k zobrazení",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }

        // TODO Je lepší přidávat data takto (pomocí více LazyColumn), nebo v jednom Lazy Column před první prvek?
        for (todos in splitTodos) {

            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Text(
                text = formatDate(todos[0].date.orEmpty()),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.secondary
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
//                    .weight(1F),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(
                    items = todos,
                    key = { todo -> todo.id },
                ) { todo ->
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
}