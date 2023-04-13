package cz.uhk.umte.ui.navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import cz.uhk.umte.ui.navigateAddTodo
import cz.uhk.umte.ui.navigateAllTodos
import cz.uhk.umte.ui.navigateTodayTodos


@Composable
fun Navbar (
    navController: NavHostController
) {
    BottomNavigation {
        BottomNavigationItem(
            label = { Text(text = "Nezařazené") },
            onClick = { navController.navigateAllTodos() },
            icon = { Icon(Icons.Default.Notifications, "") },
            selected = true
        )
        BottomNavigationItem(
            label = { Text(text = "Podle data") },
            onClick = { navController.navigateAllTodos() },
            icon = { Icon(Icons.Default.DateRange, "") },
            selected = true
        )
        BottomNavigationItem(
            label = { Text(text = "Přidat") },
            onClick = { navController.navigateAddTodo() },
            icon = { Icon(Icons.Default.Add, "") },
            selected = true
        )
    }
}