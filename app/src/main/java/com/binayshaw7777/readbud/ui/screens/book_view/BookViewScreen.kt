package com.binayshaw7777.readbud.ui.screens.book_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.utils.Logger
import com.binayshaw7777.readbud.utils.jsonToHashMap
import eu.wewox.pagecurl.ExperimentalPageCurlApi
import eu.wewox.pagecurl.page.PageCurl
import java.util.Locale
import java.util.regex.Pattern

private val WHITESPACE = Pattern.compile("\\s+")

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPageCurlApi::class)
@Composable
fun BookViewScreen(scansViewModel: ScansViewModel) {

    val scannedDocument = scansViewModel.selectedScanDocument.value!!
    val listOfPages = scannedDocument.pages
    val wordMeanings = jsonToHashMap(scannedDocument.wordMeaningsJson)

    Logger.debug("Map of jargon words: $wordMeanings")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        scannedDocument.scanName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            PageCurl(count = listOfPages.size) { index ->
                val annotatedString = getAnnotatedString(
                    WHITESPACE.split(listOfPages[index].trim()).toList(),
                    wordMeanings
                )
                PagePreview(index, annotatedString)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagePreview(
    index: Int,
    annotatedString: AnnotatedString,
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
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            DisplayParagraphWithMeanings(
                annotatedString,
                onClick = { pair ->
                    selectedPair.value = pair
                    showBottomSheet.value = true
                    Logger.debug("Pair : $pair")
                }
            )
        }
        Text(
            text = index.toString(),
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(
                    MaterialTheme.colorScheme.onBackground,
                    RoundedCornerShape(topStartPercent = 100)
                )
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Definition(
    showBottomSheet: MutableState<Boolean>,
    selectedPair: MutableState<Pair<String, String>>,
    bottomSheetState: SheetState
) {

    // Sheet content
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
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
                Text(
                    text = selectedPair.value.first,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = selectedPair.value.second)
            }
        }
    }
}

@Composable
fun DisplayParagraphWithMeanings(
    annotatedString: AnnotatedString,
    onClick: (Pair<String, String>) -> Unit
) {
    val scroll = rememberScrollState(0)

    ClickableText(
        modifier = Modifier.verticalScroll(scroll),
        text = annotatedString,
        style = TextStyle(fontSize = 16.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.Serif, lineHeight = 40.sp),
        onClick = { offset ->
            val annotations = annotatedString.getStringAnnotations(
                tag = "meaning",
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
            Logger.debug("Word and meaning: $word - $meaning")
            if (meaning.isNullOrEmpty()) {
                append("$word ")
            } else {
                pushStringAnnotation(tag = "meaning", annotation = meaning)
                withStyle(
                    style = SpanStyle(
                        background = Color.Yellow,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    Logger.debug("Appending with style: $word")
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
