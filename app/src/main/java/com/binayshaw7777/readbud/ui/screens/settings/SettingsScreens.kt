package com.binayshaw7777.readbud.ui.screens.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.SettingsListItem
import com.binayshaw7777.readbud.components.StepList
import com.binayshaw7777.readbud.model.HelpItem
import com.binayshaw7777.readbud.model.SettingsItems
import com.binayshaw7777.readbud.utils.Constants.APP_URL

@Composable
fun SettingsScreens() {

    val context = LocalContext.current
    val settingsItem = getSettingsItems(context)

    val onClickItem = remember { mutableIntStateOf(-1) }

    val showAboutBottomSheet = remember {
        mutableStateOf(false)
    }
    val showHelpBottomSheet = remember {
        mutableStateOf(false)
    }

    if (showAboutBottomSheet.value) {
        ShowAboutBottomSheet(showAboutBottomSheet)
    }

    if (showHelpBottomSheet.value) {
        ShowHelpBottomSheet(showHelpBottomSheet)
    }

    if (onClickItem.intValue != -1) {
        val clickItemIs = SettingsItems.getItemNameFromString(
            settingsItem[onClickItem.intValue].second
        )

        when (clickItemIs) {
            SettingsItems.NEED_HELP -> {
                showHelpBottomSheet.value = true
            }

            SettingsItems.SHARE -> {
                invokeSharing(context)
            }

            SettingsItems.RATE -> {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(APP_URL))
                )
            }

            SettingsItems.ABOUT -> {
                showAboutBottomSheet.value = true
            }

            else -> {}
        }
        onClickItem.intValue = -1
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
                    Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(count = settingsItem.size) {
                            SettingsListItem(
                                icon = settingsItem[it].first,
                                title = settingsItem[it].second,
                                showThemeSwitch = it == 0,
                                onClickSettingsItem = {
                                    onClickItem.intValue = it
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

fun getHelpItems(context: Context): List<HelpItem> {
    return listOf(
        HelpItem(
            stepNo = 0,
            stepDescription = context.getString(R.string.click_on_the_floating_action_button_on_the_bottom_right_side_of_your_screen_in_homepage)
        ),
        HelpItem(
            stepNo = 1,
            stepDescription = context.getString(R.string.click_on_the_the_camera_icon_on_the_bottom_right_of_your_screen_and_click_an_image_of_a_page_containing_texts)
        ),
        HelpItem(
            stepNo = 2,
            stepDescription = context.getString(R.string.repeat_the_same_process_if_you_want_to_add_multiple_images_else_you_can_save_by_click_on_top_right_button_tick_mark)
        ),
        HelpItem(
            stepNo = 3,
            stepDescription = context.getString(R.string.once_you_save_it_should_appear_on_homepage_by_clicking_any_scanned_document_it_will_take_you_to_the_book_view_page_where_you_can_view_the_texts_with_their_meanings)
        ),
        HelpItem(
            stepNo = 4,
            stepDescription = context.getString(R.string.click_on_the_highlighted_texts_to_get_their_meaning_also_you_can_click_on_the_speaker_icon_to_invoke_tts_text_to_speech)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowHelpBottomSheet(showHelpBottomSheet: MutableState<Boolean>) {
    ModalBottomSheet(
        onDismissRequest = { showHelpBottomSheet.value = false },
        sheetState = SheetState(skipPartiallyExpanded = true, density = LocalDensity.current)
    ) {
        val context = LocalContext.current
        val helpItems = getHelpItems(context)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.how_to_use_readbud),
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(20.dp))
            LazyColumn(Modifier.fillMaxWidth()) {
                items(helpItems.size) {
                    StepList(helpItems[it].stepNo, helpItems[it].stepDescription)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAboutBottomSheet(showAboutBottomSheet: MutableState<Boolean>) {
    ModalBottomSheet(
        onDismissRequest = { showAboutBottomSheet.value = false },
        sheetState = SheetState(skipPartiallyExpanded = true, density = LocalDensity.current)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            item {
                Image(
                    modifier = Modifier
                        .clip(CircleShape.copy(CornerSize(40.dp)))
                        .size(80.dp),
                    painter = painterResource(R.drawable.circular_logo),
                    contentDescription = stringResource(R.string.app_name)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = stringResource(R.string.app_name),
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = stringResource(R.string.about_app_description),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.purpose_app_description),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.need_help_note),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
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