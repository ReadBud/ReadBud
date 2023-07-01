package com.binayshaw7777.readbud.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.model.SettingsItems
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.utils.Logger

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreens(navController: NavHostController) {

    val settingsItem = arrayListOf<Pair<Int, String>>()
    settingsItem.add(Pair(R.drawable.appearance_icon, stringResource(R.string.appearance)))
    settingsItem.add(Pair(R.drawable.storage_icon, stringResource(R.string.storage_and_data)))
    settingsItem.add(Pair(R.drawable.about_icon, stringResource(R.string.about)))

//    val isDarkTheme = remember { mutableStateOf(true) }

    val onClickItem = remember {
        mutableStateOf(-1)
    }


    ReadBudTheme(dynamicColor = true) {

        if (onClickItem.value != -1) {
            when (SettingsItems.getItemNameFromString(
                settingsItem[onClickItem.value].second
            )
            ) {
                SettingsItems.APPEARANCE -> {
//                    isDarkTheme.value = isDarkTheme.value.not()
                }

                SettingsItems.STORAGE -> {

                }

                SettingsItems.ABOUT -> {

                }
            }
            onClickItem.value = -1
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.settings),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                )
            },
            content = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(20.dp, 0.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            stringResource(R.string.general),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(
                            Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(count = settingsItem.size) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            onClickItem.value = it
                                        }, horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = settingsItem[it].first),
                                        contentDescription = settingsItem[it].second,
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        settingsItem[it].second,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingsScreens(rememberNavController())
}
