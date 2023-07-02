package com.binayshaw7777.readbud.ui.screens.book_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.utils.Logger
import com.binayshaw7777.readbud.utils.getWordMeaningFromString
import eu.wewox.pagecurl.ExperimentalPageCurlApi
import eu.wewox.pagecurl.page.PageCurl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPageCurlApi::class)
@Composable
fun BookViewScreen(scansViewModel: ScansViewModel) {

    val scannedDocument = scansViewModel.selectedScanDocument.value
    val listOfPages = ArrayList<String>()
    val context = LocalContext.current
    for (page in scannedDocument!!.listOfScans) {
        page.extractedText?.let { listOfPages.add(it) }
    }
    val currentIndex = remember {
        mutableStateOf(0)
    }
    val mapOfJargonWords = HashMap<String, String>()
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            getWordMeaningFromString(listOfPages[currentIndex.value], context)
        }
    }

    Logger.debug("Scanned doc: $scannedDocument \nand listOfPages: $listOfPages")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Scan Preview",
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
                currentIndex.value = index
                PagePreview(index, listOfPages[index])
            }
        }
    }

}

@Composable
fun PagePreview(
    index: Int,
    page: String,
    modifier: Modifier = Modifier
) {
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
            Text(
                text = page,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
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