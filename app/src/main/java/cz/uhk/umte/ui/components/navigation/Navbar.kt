package cz.uhk.umte.ui.components.navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import cz.uhk.umte.ui.*


@Composable
fun Navbar (
    navController: NavHostController
) {
    BottomNavigation {
        BottomNavigationItem(
            label = { Text(text = "Nezařazené") },
            onClick = { navController.navigateTodosWithoutDate() },
            icon = { Icon(Icons.Default.Notifications, "") },
            selected = true
        )
        BottomNavigationItem(
            label = { Text(text = "Podle data") },
            onClick = { navController.navigateTodosDate() },
            icon = { Icon(Icons.Default.DateRange, "") },
            selected = true
        )
        BottomNavigationItem(
            label = { Text(text = "Přidat") },
            onClick = { navController.navigateAddTodo() },
            icon = { Icon(Icons.Default.Add, "") },
            selected = true
        )
        BottomNavigationItem(
            label = { Text(text = "Nastavení") },
            onClick = { navController.navigateSettings() },
            icon = { Icon(Icons.Default.Settings, "") },
            selected = true
        )
    }
}