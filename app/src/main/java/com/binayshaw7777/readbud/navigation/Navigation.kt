package com.binayshaw7777.readbud.navigation

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
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.ui.screens.book_view.BookViewScreen
import com.binayshaw7777.readbud.ui.screens.helpers.MLKitTextRecognition
import com.binayshaw7777.readbud.ui.screens.home.HomeScreen
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.screens.image_screens.image_listing.ImageListing
import com.binayshaw7777.readbud.ui.screens.settings.SettingsScreens
import com.binayshaw7777.readbud.utils.Constants.BOOK_VIEW
import com.binayshaw7777.readbud.utils.Constants.HOME
import com.binayshaw7777.readbud.utils.Constants.IMAGE_LISTING
import com.binayshaw7777.readbud.utils.Constants.ML_KIT_RECOGNITION
import com.binayshaw7777.readbud.utils.Constants.SETTINGS


@Composable
fun Navigation(imageViewModel: ImageViewModel) {
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
        Screens.MLKitTextRecognition.name, "${Screens.BookView.name}/{scanId}"
    )
    Scaffold(
        bottomBar = {
            if (backStackEntry.value?.destination?.route !in screensWithoutNavBar) {
                NavigationBar {
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

        NavHost(
            navController,
            startDestination = HOME,
            modifier = Modifier
                .padding(it)
        ) {
            composable(HOME) {
                val scansViewModel = hiltViewModel<ScansViewModel>()
                HomeScreen(scansViewModel, navController, onItemClicked = { scanId ->
                    navController.navigate("${Screens.BookView.name}/$scanId")
                })
            }
            composable(
                route = "${BOOK_VIEW}/{scanId}",
                arguments = listOf(navArgument(name = "scanId") { type = IntType })
            ) { backStackEntry ->
                val scanId = backStackEntry.arguments?.getInt("scanId") ?: 0
                val scanViewModel = hiltViewModel<ScansViewModel>()
                BookViewScreen(scanId, scanViewModel, navController)
            }
            composable(SETTINGS) { SettingsScreens(navController) }
            composable(IMAGE_LISTING) {
                val scanViewModel = hiltViewModel<ScansViewModel>()
                ImageListing(imageViewModel, scanViewModel, navController)
            }
            composable(ML_KIT_RECOGNITION) {
                MLKitTextRecognition(navController, imageViewModel)
            }
        }
    }
}