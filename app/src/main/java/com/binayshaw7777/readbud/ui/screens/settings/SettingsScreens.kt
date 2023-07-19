package com.binayshaw7777.readbud.ui.screens.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.ThemeSwitch
import com.binayshaw7777.readbud.model.SettingsItems
import com.binayshaw7777.readbud.utils.Constants.APP_URL

@Composable
fun SettingsScreens() {

    val context = LocalContext.current
    val settingsItem = getSettingsItems(context)

    val onClickItem = remember { mutableStateOf(-1) }

    if (onClickItem.value != -1) {
        val clickItemIs = SettingsItems.getItemNameFromString(
            settingsItem[onClickItem.value].second
        )

        when (clickItemIs) {
            SettingsItems.NEED_HELP -> {

            }

            SettingsItems.SHARE -> {
                invokeSharing(context)
            }

            SettingsItems.RATE -> {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(APP_URL))
                )
            }

            SettingsItems.STORAGE -> {

            }

            SettingsItems.ABOUT -> {

            }

            else -> {}
        }
        onClickItem.value = -1
    }

    Scaffold(
        topBar = { ShowTopAppBar() },
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
                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(count = settingsItem.size) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .clickable { onClickItem.value = it },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row {
                                    Icon(
                                        painter = painterResource(id = settingsItem[it].first),
                                        contentDescription = settingsItem[it].second,
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        settingsItem[it].second,
                                    )
                                }
                                Row {
                                    if (it == 0) {
                                        ThemeSwitch(themeViewModel = hiltViewModel())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

fun invokeSharing(context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.on_share_message))
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

fun getSettingsItems(context: Context): List<Pair<Int, String>> {
    val resources = context.resources
    return arrayListOf(
        Pair(R.drawable.appearance_icon, resources.getString(R.string.appearance)),
        Pair(R.drawable.storage_icon, resources.getString(R.string.storage_and_data)),
        Pair(R.drawable.help_icon, resources.getString(R.string.need_help)),
        Pair(R.drawable.info_icon, resources.getString(R.string.about)),
        Pair(R.drawable.share_icon, resources.getString(R.string.share)),
        Pair(R.drawable.rate_icon, resources.getString(R.string.rate))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = R.string.settings),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        )
    )
}
