package com.binayshaw7777.readbud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.binayshaw7777.readbud.ui.screens.home.HomeScreen
import com.binayshaw7777.readbud.ui.screens.settings.SettingsScreens


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home_screen"
    ) {
        composable("home_screen") {
            HomeScreen(navController = navController)
        }
        composable("settings_screen") {
            SettingsScreens(navController = navController)
        }
    }
}