package com.binayshaw7777.readbud.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ImageSourceOptions() {
    val imageSelectOptions = listOf(
        "Camera  📸",
        "Gallery  🖼"
    )

    LazyColumn {
        items(imageSelectOptions) { optionText ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = optionText,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
    }
}