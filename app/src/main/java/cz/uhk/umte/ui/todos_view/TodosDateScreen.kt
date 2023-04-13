package cz.uhk.umte.ui.todos_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.uhk.umte.ui.navigateTodoDetail
import cz.uhk.umte.ui.todo.Todo
import org.koin.androidx.compose.getViewModel

@Composable
fun TodosAllScreen (
    controller: NavHostController,
    viewModel: TodosDateVM = getViewModel(),
) {
    // Vytažení všech TODOS z VM
    val todos = viewModel.todos.collectAsState(emptyList())

    // Sestavení pohledu
    Column {
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
                    date = todo.date.orEmpty(),
                    handleChecked = { viewModel.handleTodoCheck(todo) },
                    handleDetail = { controller.navigateTodoDetail(todo.id) },
                )
            }
        }
    }
}