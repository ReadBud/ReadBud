package com.binayshaw7777.readbud.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.binayshaw7777.readbud.navigation.Navigation
import com.binayshaw7777.readbud.viewmodel.ImageSharedViewModel
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.loading.value
            }
        }
        setContent {
            ReadBudTheme(dynamicColor = true) {
                Surface {
                    val imageSharedViewModel = hiltViewModel<ImageSharedViewModel>()
                    Navigation(imageSharedViewModel)
                }
            }
        }
    }
}