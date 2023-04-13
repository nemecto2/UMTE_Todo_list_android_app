package cz.uhk.umte.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.uhk.umte.ui.todo_add.TodoAddScreen
import cz.uhk.umte.ui.todo_detail.TodoDetailScreen
import cz.uhk.umte.ui.todos_view.TodosAllScreen

@Composable
fun Layout(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = DestinationAllTodos,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
    ) {
        composable(
            route = DestinationAllTodos,
        ) {
            TodosAllScreen(
                controller = navController,
            )
        }

        composable(
            route = DestinationAddTodo,
        ) {
            TodoAddScreen(
                controller = navController,
            )
        }

        composable(
            route = DestinationTodoDetail,
        ) { navBackStackEntry ->
            TodoDetailScreen(
                controller = navController,
                todoId = navBackStackEntry.arguments?.getString(ArgTodoId).orEmpty().toLong(),
            )
        }
    }
}


// Destinations
private const val ArgTodoId = "argTodoId"

private const val DestinationAllTodos = "all_todos"
private const val DestinationTodayTodos = "today_todos"
private const val DestinationAddTodo = "add_todo"
private const val DestinationTodoDetail = "todo_detail/{$ArgTodoId}"


// Navigate functions
fun NavHostController.navigateAllTodos() {
    if (currentDestination?.route.equals(DestinationAllTodos).not()) {
        navigate(DestinationAllTodos)
    }
}

fun NavHostController.navigateTodayTodos() {
    navigate(DestinationTodayTodos)
}

fun NavHostController.navigateAddTodo() {
    if (currentDestination?.route.equals(DestinationAddTodo).not()) {
        navigate(DestinationAddTodo)
    }
}

fun NavHostController.navigateTodoDetail(id: Long) {
    navigate(DestinationTodoDetail.replaceArg(ArgTodoId, id.toString()))
}


//fun NavHostController.navigateWithPopUp(
//    toRoute: String,  // route name where you want to navigate
//    fromRoute: String // route you want from popUpTo.
//) {
//    this.navigate(toRoute) {
//        popUpTo(fromRoute) {
//            inclusive = true // It can be changed to false if you
//            // want to keep your fromRoute exclusive
//        }
//    }
//}

private fun String.replaceArg(argName: String, value: String) =
    replace("{$argName}", value)

