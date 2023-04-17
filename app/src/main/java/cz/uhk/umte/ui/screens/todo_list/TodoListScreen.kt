package cz.uhk.umte.ui.screens.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.uhk.umte.ui.navigateTodoDetail
import cz.uhk.umte.ui.components.todo.Todo
import org.koin.androidx.compose.getViewModel

@Composable
fun TodoListScreen (
    controller: NavHostController,
    viewModel: TodoListVM = getViewModel(),
) {
    // Vytažení všech TODOS z VM
    val todos = viewModel.todos.collectAsState(emptyList())

    // Sestavení pohledu
    Column {
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
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(
                items = todos.value,
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