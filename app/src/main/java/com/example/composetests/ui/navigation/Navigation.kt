package com.example.composetests.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetests.ui.screens.MainScreen
import com.example.composetests.ui.screens.ObservableScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.name){
        composable(Screens.MainScreen.name){
            MainScreen(navController = navController)
        }

        composable(Screens.ObservableScreen.name){
            ObservableScreen(navController = navController)
        }
    }
}

