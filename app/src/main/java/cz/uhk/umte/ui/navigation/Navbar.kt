package cz.uhk.umte.ui.navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import cz.uhk.umte.ui.navigateAllTodos
import cz.uhk.umte.ui.navigateTodayTodos


@Composable
fun Navbar (
    navController: NavHostController
) {
    BottomNavigation {
        BottomNavigationItem(
            label = { Text(text = "Všechny") },
            onClick = { navController.navigateAllTodos() },
            icon = { Icon(Icons.Default.Add, "") },
            selected = true
        )
        BottomNavigationItem(
            label = { Text(text = "Dnes") },
            onClick = { navController.navigateTodayTodos() },
            icon = { Icon(Icons.Default.AccountBox, "") },
            selected = true
        )
        BottomNavigationItem(
            label = { Text(text = "Podle data") },
            onClick = { navController.navigateTodayTodos() },
            icon = { Icon(Icons.Default.Add, "") },
            selected = true
        )
    }
}