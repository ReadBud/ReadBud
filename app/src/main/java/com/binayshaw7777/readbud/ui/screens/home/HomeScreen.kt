package com.binayshaw7777.readbud.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binayshaw7777.readbud.ui.theme.PrimaryContainer


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        var searchQuery by rememberSaveable { mutableStateOf("") }

        SearchBar(
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth()
                .padding(20.dp),
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text("Search your last scan") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        ) {
        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.End).padding(20.dp),
            onClick = {
                //OnClick Method
            },
            containerColor = PrimaryContainer,
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add FAB",
                tint = Color.White,
            )
        }
    }
}