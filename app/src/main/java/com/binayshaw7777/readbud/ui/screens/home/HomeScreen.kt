package com.binayshaw7777.readbud.ui.screens.home


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.SimpleCardDisplay
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.navigation.Screens
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun HomeScreen(
    scansViewModel: ScansViewModel,
    navController: NavController,
    onItemClicked: (scanId: Int) -> Unit
) {

    val listOfAllScans by scansViewModel.listOfScans.observeAsState(listOf())

    var searchBarFilterQuery by remember {
        mutableStateOf("")
    }
    val showDeleteAllDialog = remember { mutableStateOf(false) }

    //If the delete all button is clicked (Top right of App bar)
    if (showDeleteAllDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDeleteAllDialog.value = false
            },
            icon = { Icon(Icons.Filled.Delete, contentDescription = null) },
            title = {
                Text(text = stringResource(R.string.delete_all_scans))
            },
            text = {
                Text(
                    stringResource(R.string.do_you_want_to_clear_all_scans_permanently)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scansViewModel.deleteAllScans()
                        showDeleteAllDialog.value = false
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteAllDialog.value = false
                    }
                ) {
                    Text(stringResource(R.string.don_t_delete))
                }
            }
        )
    }


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
                actions = {
                    if (listOfAllScans.isNotEmpty()) {
                        IconButton(onClick = { showDeleteAllDialog.value = true }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete_action)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        }, floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(20.dp),
                onClick = {
                    navController.navigate(Screens.ItemListing.name)
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
                SearchBar(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    query = searchBarFilterQuery,
                    onQueryChange = {
                        searchBarFilterQuery = it
                    },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text(stringResource(R.string.search_your_last_scan)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchBarFilterQuery.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable { searchBarFilterQuery = "" },
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }
                ) {
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    items(listOfAllScans.filter {
                        it.scanName.contains(
                            searchBarFilterQuery,
                            ignoreCase = true
                        )
                    }) { item ->
                        SimpleCardDisplay(
                            onClick = {
                                onItemClicked(item.id)
                            },
                            heading = item.scanName,
                        )
                    }
                }
            }
        }
    }
}