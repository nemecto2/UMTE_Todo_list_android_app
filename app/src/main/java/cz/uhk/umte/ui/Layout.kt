package cz.uhk.umte.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.uhk.umte.ui.screens.settings.SettingsScreen
import cz.uhk.umte.ui.screens.todo_add.TodoAddScreen
import cz.uhk.umte.ui.screens.todo_detail.TodoDetailScreen
import cz.uhk.umte.ui.screens.todo_list.TodoListScreen
import cz.uhk.umte.ui.screens.todo_list_date.TodoListDateScreen
import org.koin.androidx.compose.getViewModel


//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Layout(
    navController: NavHostController,
    viewModel: LayoutVM = getViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = DestinationTodosDate,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
    ) {
        composable(
            route = DestinationTodosDate,
        ) {
            TodoListDateScreen(
                controller = navController,
            )
        }

        composable(
            route = DestinationTodosWithoutDate,
        ) {
            TodoListScreen(
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

        composable(
            route = DestinationSettings,
        ) {
            SettingsScreen()
        }
    }
}


// Destinations
private const val ArgTodoId = "argTodoId"

private const val DestinationTodosWithoutDate = "todos_without_date"
private const val DestinationTodosDate = "todos_date"
private const val DestinationAddTodo = "add_todo"
private const val DestinationTodoDetail = "todo_detail/{$ArgTodoId}"
private const val DestinationSettings = "note_list"


// Navigate functions
fun NavHostController.navigateTodosDate() {
    if (currentDestination?.route.equals(DestinationTodosDate).not()) {
        navigate(DestinationTodosDate)
    }
}

fun NavHostController.navigateTodosWithoutDate() {
    if (currentDestination?.route.equals(DestinationTodosWithoutDate).not()) {
        navigate(DestinationTodosWithoutDate)
    }
}

fun NavHostController.navigateAddTodo() {
    if (currentDestination?.route.equals(DestinationAddTodo).not()) {
        navigate(DestinationAddTodo)
    }
}

fun NavHostController.navigateTodoDetail(id: Long) {
    navigate(DestinationTodoDetail.replaceArg(ArgTodoId, id.toString()))
}

fun NavHostController.navigateSettings() {
    if (currentDestination?.route.equals(DestinationSettings).not()) {
        navigate(DestinationSettings)
    }
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

