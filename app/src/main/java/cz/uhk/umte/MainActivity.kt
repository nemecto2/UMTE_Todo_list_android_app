package cz.uhk.umte

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import cz.uhk.umte.ui.Layout
import cz.uhk.umte.ui.components.navigation.Navbar
import cz.uhk.umte.ui.theme.UMTETheme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UMTETheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { Navbar(navController = navController) }
                ) {
                    Layout(navController)
                }
            }
        }
    }
}