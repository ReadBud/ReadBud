package com.binayshaw7777.readbud.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.binayshaw7777.readbud.ui.screens.home.HomeScreen
import com.binayshaw7777.readbud.ui.screens.settings.SettingsScreens


@Composable
fun Navigation() {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Settings",
            route = "settings",
            icon = Icons.Rounded.Settings,
        ),
    )

    val navController = rememberNavController()
    // NavController is passed via parameter
    val backStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                                tint = if (backStackEntry.value?.destination?.route == item.route)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        label = {
                            Text(
                                text = item.name,
                                color = if (backStackEntry.value?.destination?.route == item.route)
                                    MaterialTheme.colorScheme.inverseOnSurface
                                else
                                    MaterialTheme.colorScheme.onPrimary,
                                fontWeight = if (backStackEntry.value?.destination?.route == item.route)
                                    FontWeight.Bold
                                else
                                    FontWeight.Normal,
                            )
                        },
                        selected = backStackEntry.value?.destination?.route == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController,
            startDestination = "home",
            modifier = Modifier.padding(it).background(Color.White)
        ) {
            composable("home") { HomeScreen() }
            composable("settings") { SettingsScreens() }
        }
    }
}