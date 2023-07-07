package com.binayshaw7777.readbud.ui.screens.home


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.SimpleCardDisplay
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.model.Scans
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.utils.Logger


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun HomeScreen(
    scansViewModel: ScansViewModel,
    onFabClicked: () -> Unit,
    navigateToBookView: () -> Unit
) {

    val listOfAllScans by scansViewModel.listOfScans.observeAsState(listOf())

    val selectedItem = remember {
        mutableStateOf(Scans(0, "", ArrayList(), ""))
    }
    var isSelected by remember { mutableStateOf(false) }

    if (isSelected) {
        Logger.debug("Selected item: $selectedItem")
        scansViewModel.selectedScanDocument.postValue(selectedItem.value)
        isSelected = false
        navigateToBookView()
    }

    ReadBudTheme(dynamicColor = true) {
        Scaffold(Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.home),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                )
            }, floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = {
                        onFabClicked()
                    },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_fab),
                    )
                }
            }) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {

                    var searchQuery by rememberSaveable { mutableStateOf("") }

                    SearchBar(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp),
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = { Text(stringResource(R.string.search_your_last_scan)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                    ) {
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (listOfAllScans.isNotEmpty()) {
                        Logger.debug("All items: $listOfAllScans")
                        LazyColumn {
                            items(listOfAllScans) { item ->
                                SimpleCardDisplay(
                                    onClick = {
                                        selectedItem.value = item
                                        isSelected = true
                                    },
                                    heading = item.scanName,
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
