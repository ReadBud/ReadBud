package com.binayshaw7777.readbud.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binayshaw7777.readbud.ui.screens.home.HomeScreen

@Composable
fun SettingsScreens(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize().background(Color.White)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingsScreens(rememberNavController())
}
