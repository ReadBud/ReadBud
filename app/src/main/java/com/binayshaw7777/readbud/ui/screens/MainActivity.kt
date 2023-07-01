package com.binayshaw7777.readbud.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.binayshaw7777.readbud.navigation.Navigation
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadBudTheme {

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                val statusBarColor = MaterialTheme.colorScheme.background
                val navigationBarColor = MaterialTheme.colorScheme.primary
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = navigationBarColor, darkIcons = useDarkIcons
                    )

                    systemUiController.setStatusBarColor(
                        color = statusBarColor, darkIcons = useDarkIcons
                    )
                }
                // A surface container using the 'background' color from the theme
                Surface {
                    Navigation()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReadBudTheme {
        Navigation()
    }
}