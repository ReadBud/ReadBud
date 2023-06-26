package com.binayshaw7777.readbud.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeScreen(rememberNavController())
}