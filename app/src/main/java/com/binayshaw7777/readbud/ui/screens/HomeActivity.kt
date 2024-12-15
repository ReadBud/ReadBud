package com.binayshaw7777.readbud.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.binayshaw7777.readbud.navigation.Navigation
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.viewmodel.ImageSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            ReadBudTheme(dynamicColor = true) {
                val imageSharedViewModel = hiltViewModel<ImageSharedViewModel>()
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    Navigation(imageSharedViewModel)
                }
            }
        }
    }
}