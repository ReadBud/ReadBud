package com.binayshaw7777.readbud.ui.screens.book_view

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.bottom_sheets.TextStyleBottomSheetModal
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.model.Scans
import com.binayshaw7777.readbud.utils.Constants.MEANING
import com.binayshaw7777.readbud.utils.getFontWeights
import com.binayshaw7777.readbud.utils.getTypeFaces
import com.binayshaw7777.readbud.utils.jsonToHashMap
import java.util.Locale
import java.util.regex.Pattern

//Just a pattern checking code that processes at compile-time
private val WHITESPACE_PATTERN = Pattern.compile("\\s+")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookViewScreen(
    scanId: Int,
    scansViewModel: ScansViewModel,
    navController: NavController
) {

    var listOfPages: List<String> = listOf()
    val listOfAnnotatedString: ArrayList<AnnotatedString> = arrayListOf()
    var wordMeanings: HashMap<String, String> = hashMapOf()

    var fontSize by remember { mutableStateOf(16.sp) }

    val availableTypefaces = getTypeFaces()
    var selectedTypeface by remember { mutableStateOf(availableTypefaces[0]) }

    val availableFontWeight = getFontWeights()
    var selectedFontWeight by remember { mutableStateOf(FontWeight.Normal) }

    var selectedLineHeight by remember { mutableStateOf(18.sp) }

    val scanItemResult by scansViewModel.scanItemLiveData.observeAsState()

    val showBottomSheet = remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showBottomSheet.value) {
        TextStyleBottomSheetModal(bottomSheetState, showBottomSheet, onFontSizeChange = {
            fontSize = it
        }, availableTypefaces, onFontChange = {
            selectedTypeface = it
        }, availableFontWeight, onFontWeightChange = {
            selectedFontWeight = it
        }, onLineHeightChange = {
            selectedLineHeight = it
        })
    }

    scanItemResult?.let {
        if (it.pages.isNotEmpty() && wordMeanings.isEmpty()) {
            listOfPages = it.pages
            wordMeanings = jsonToHashMap(it.wordMeaningsJson)

            for (page in listOfPages.indices) {
                val annotatedString = getAnnotatedString(
                    WHITESPACE_PATTERN.split(listOfPages[page].trim()).toList(),
                    wordMeanings
                )
                listOfAnnotatedString.add(annotatedString)
            }
        }
    }

    LaunchedEffect(Unit) {
        scansViewModel.getScanById(scanId)
    }

    Scaffold(
        topBar = {
            ShowTopAppBar(scanItemResult, navController) {
                showBottomSheet.value = true
            }
        }

    ) { padding ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                itemsIndexed(listOfPages) { index, _ ->
                    PagePreview(
                        index = index,
                        annotatedString = listOfAnnotatedString,
                        fontSize = fontSize,
                        fontFamily = selectedTypeface,
                        fontWeight = selectedFontWeight,
                        lineHeight = selectedLineHeight
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .height(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Page: ${index + 1}",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTopAppBar(
    scanItemResult: Scans?,
    navController: NavController,
    onShowBookPreviewPreferences: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                scanItemResult?.scanName.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.go_back)
                )
            }
        },
        actions = {
            IconButton(onClick = { onShowBookPreviewPreferences() }) {
                Icon(
                    painterResource(id = R.drawable.font_icon),
                    contentDescription = stringResource(R.string.book_preview_preferences)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagePreview(
    index: Int,
    annotatedString: ArrayList<AnnotatedString>,
    fontSize: TextUnit,
    fontFamily: SystemFontFamily,
    fontWeight: FontWeight,
    lineHeight: TextUnit,
    modifier: Modifier = Modifier
) {

    val showBottomSheet = remember {
        mutableStateOf(false)
    }
    val selectedPair = remember {
        mutableStateOf(
            Pair("", "")
        )
    }
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    Definition(showBottomSheet, selectedPair, bottomSheetState)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            DisplayParagraphWithMeanings(
                annotatedString[index],
                fontSize,
                fontFamily,
                fontWeight,
                lineHeight = lineHeight,
                onClick = { pair ->
                    selectedPair.value = pair
                    showBottomSheet.value = true
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Definition(
    showBottomSheet: MutableState<Boolean>,
    selectedPair: MutableState<Pair<String, String>>,
    bottomSheetState: SheetState
) {
    val context = LocalContext.current
    var textToSpeech: TextToSpeech? = null

    var invokeTTS by remember {
        mutableStateOf(false)
    }

    if (invokeTTS) {
        textToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS && textToSpeech?.isSpeaking?.not() == true) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.US
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        selectedPair.value.second,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }
        }
        invokeTTS = false
    }

    // Sheet content
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                textToSpeech?.stop()
                textToSpeech?.shutdown()
                showBottomSheet.value = false
            },
            sheetState = bottomSheetState,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(20.dp)
            ) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .size(40.dp),
                        onClick = {
                            invokeTTS = true
                        },
                        content = {
                            Icon(
                                painter = painterResource(
                                    id = if (textToSpeech?.isSpeaking == true) {
                                        R.drawable.stop_icon
                                    } else R.drawable.speaker_icon
                                ),
                                contentDescription = stringResource(R.string.text_to_speech),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        })

                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = selectedPair.value.first.lowercase(),
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = FontFamily.Serif
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = selectedPair.value.second)
            }
        }
    }
}

@Composable
fun DisplayParagraphWithMeanings(
    annotatedString: AnnotatedString,
    fontSize: TextUnit,
    fontFamily: SystemFontFamily,
    fontWeight: FontWeight,
    lineHeight: TextUnit,
    onClick: (Pair<String, String>) -> Unit
) {

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontSize = fontSize,
            letterSpacing = 2.sp,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            lineHeight = lineHeight,
        ),
        onClick = { offset ->
            val annotations = annotatedString.getStringAnnotations(
                tag = MEANING,
                start = offset,
                end = offset
            )
            if (annotations.isNotEmpty()) {
                val word = annotatedString.text.substring(
                    annotations[0].start,
                    annotations[0].end
                )
                onClick(
                    Pair(
                        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() },
                        annotations[0].item
                    )
                )
            }
        })
}

fun getAnnotatedString(words: List<String>, wordMeanings: Map<String, String>): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        for (word in words) {
            val meaning = wordMeanings[word.lowercase()]

            if (meaning.isNullOrEmpty()) {
                append("$word ")
            } else {
                pushStringAnnotation(tag = MEANING, annotation = meaning)
                withStyle(
                    style = SpanStyle(
                        background = Color.Yellow,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(word)
                }
                append(" ")
                pop()
            }
        }
        toAnnotatedString()
    }
    return annotatedString
}