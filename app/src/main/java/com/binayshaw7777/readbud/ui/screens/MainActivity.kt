package com.binayshaw7777.readbud.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.binayshaw7777.readbud.navigation.Navigation
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadBudTheme(dynamicColor = true) {
                Surface {
                    val imageViewModel = hiltViewModel<ImageViewModel>()
                    Navigation(imageViewModel)
                }
            }
        }
    }
}