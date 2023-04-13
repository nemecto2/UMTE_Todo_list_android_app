package cz.uhk.umte.ui.todo_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.uhk.umte.db.entities.TodoEntity
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TodoDetailScreen(
    controller: NavHostController,
    todoId: Long,
    viewModel: TodoDetailVM = getViewModel {
        parametersOf(todoId)
    },
) {
    val todo = viewModel.todo.collectAsState(TodoEntity())

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Button(
            onClick = {
                controller.popBackStack()
                viewModel.deleteTodo(todo.value)
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

        Button(
            onClick = {},
        ) {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = "Přidat poznámku"
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Text (
                text = "Přidat poznámku"
            )
        }


        if (todo.value != null) {
            Text(text = todo.value.text)
        }
    }
}