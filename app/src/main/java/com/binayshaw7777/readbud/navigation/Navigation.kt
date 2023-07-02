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
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.ui.screens.helpers.MLKitTextRecognition
import com.binayshaw7777.readbud.ui.screens.home.HomeScreen
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.screens.image_screens.image_listing.ImageListing
import com.binayshaw7777.readbud.ui.screens.settings.SettingsScreens
import com.binayshaw7777.readbud.utils.Constants.EXTRACTED_TEXT
import com.binayshaw7777.readbud.utils.Constants.HOME
import com.binayshaw7777.readbud.utils.Constants.IMAGE_LISTING
import com.binayshaw7777.readbud.utils.Constants.ML_KIT_RECOGNITION
import com.binayshaw7777.readbud.utils.Constants.SETTINGS


@Composable
fun Navigation() {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = Screens.Home.name,
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Settings",
            route = Screens.Settings.name,
            icon = Icons.Rounded.Settings,
        ),
    )

    val navController = rememberNavController()
    // NavController is passed via parameter
    val backStackEntry = navController.currentBackStackEntryAsState()

    val screensWithoutNavBar = listOf(
        Screens.MLKitTextRecognition.name
    )
    Scaffold(
        bottomBar = {
            if (backStackEntry.value?.destination?.route !in screensWithoutNavBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            alwaysShowLabel = true,
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    tint = if (backStackEntry.value?.destination?.route == item.route)
                                        MaterialTheme.colorScheme.onSurface
                                    else
                                        MaterialTheme.colorScheme.secondary
                                )
                            },
                            label = {
                                Text(
                                    text = item.name,
                                    color = if (backStackEntry.value?.destination?.route == item.route)
                                        MaterialTheme.colorScheme.onSurface
                                    else
                                        MaterialTheme.colorScheme.secondary,
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
        }
    ) {
        val imageViewModel = ImageViewModel()
        NavHost(
            navController,
            startDestination = HOME,
            modifier = Modifier
                .padding(it)
                .background(Color.White)
        ) {
            composable(HOME) {
                HomeScreen(
                    onFabClicked = { navController.navigate(Screens.ItemListing.name) })
            }
            composable(SETTINGS) { SettingsScreens(navController) }
            composable(IMAGE_LISTING) { entry ->
                val text = entry.savedStateHandle.get<RecognizedTextItem>(EXTRACTED_TEXT)
                ImageListing(text, imageViewModel, onFabClick = {navController.navigate(Screens.MLKitTextRecognition.name)})
            }
            composable(ML_KIT_RECOGNITION) {
                MLKitTextRecognition(navController)
            }
        }
    }
}