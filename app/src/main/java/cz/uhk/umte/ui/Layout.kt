package cz.uhk.umte.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.uhk.umte.ui.todo.TodoAddScreen
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
//            TodoAddScreen()
            TodosAllScreen()
        }

        composable(
            route = DestinationTodayTodos,
        ) {
            TodoAddScreen()
//            TodosAllScreen()
        }

    }
}


// Destinations
private const val DestinationAllTodos = "all_todos"
private const val DestinationTodayTodos = "today_todos"


// Navigate functions
fun NavHostController.navigateAllTodos() {
    navigate(DestinationAllTodos)
}

fun NavHostController.navigateTodayTodos() {
    navigate(DestinationTodayTodos)
}
