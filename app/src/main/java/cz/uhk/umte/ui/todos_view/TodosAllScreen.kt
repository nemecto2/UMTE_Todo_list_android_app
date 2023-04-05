package cz.uhk.umte.ui.todos_view

import androidx.compose.runtime.Composable

@Composable
fun TodosAllScreen (
//    viewModel: TodosAllVM = getViewModel(), // TODO zde je chyba!!! Nejspíš něco s DI Koin
) {
//    // Vytažení všech TODOS z VM
//    val todos = viewModel.todos.collectAsState(emptyList())
//
//    // Sestavení pohledu
//    Column {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1F),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            contentPadding = PaddingValues(16.dp),
//        ) {
//            items(
//                items = todos.value,
//                key = { todo -> todo.id },
//            ) { todo ->
//                Todo(
//                    text = todo.text,
//                    checked = todo.checked,
//                    handleChecked = { viewModel.handleTodoCheck(todo) },
//                    handleDetail = { viewModel.handleNavigateDetail(todo) },
//                )
//            }
//        }
//    }
}