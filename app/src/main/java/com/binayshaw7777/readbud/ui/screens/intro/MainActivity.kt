package com.binayshaw7777.readbud.ui.screens.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.binayshaw7777.readbud.ui.screens.HomeActivity
import com.binayshaw7777.readbud.ui.screens.intro.component.AutoScrollingLazyRow
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.utils.Utils
import com.binayshaw7777.readbud.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val introImageList by lazy { Utils.getIntroBookImages() }
    private val bookSize = Pair(100.dp, 160.dp)
    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ReadBudTheme(dynamicColor = true) {

                val gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background
                    )
                )


                Surface {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AutoScrollingLazyRow(
                                list = (0 until 4).take(4),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = introImageList[it]),
                                    contentDescription = null,
                                    modifier = Modifier.sizeIn(
                                        minWidth = bookSize.first,
                                        minHeight = bookSize.second
                                    ),
                                    contentScale = ContentScale.FillHeight
                                )
                            }

                            AutoScrollingLazyRow(
                                list = (4 until 8).take(4),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                reverseLayout = true
                            ) {
                                Image(
                                    painter = painterResource(id = introImageList[it]),
                                    contentDescription = null,
                                    modifier = Modifier.sizeIn(
                                        minWidth = bookSize.first,
                                        minHeight = bookSize.second
                                    ),
                                    contentScale = ContentScale.FillHeight
                                )
                            }

                            AutoScrollingLazyRow(
                                list = (8 until 12).take(4),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = introImageList[it]),
                                    contentDescription = null,
                                    modifier = Modifier.sizeIn(
                                        minWidth = bookSize.first,
                                        minHeight = bookSize.second
                                    ),
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(gradient),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(horizontal = 32.dp, vertical = 40.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    16.dp,
                                    alignment = Alignment.CenterVertically
                                )
                            ) {
                                Text(
                                    text = "Simplifying Reading.",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "No more jargon while reading your\nfavorite book or documents.",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                HomeActivity::class.java
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text(text = "Get Started", color = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}