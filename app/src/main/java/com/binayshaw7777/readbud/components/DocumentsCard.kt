package com.binayshaw7777.readbud.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DocumentCard(thumbnail: Bitmap, heading: String, description: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.secondaryContainer),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(fontSize = 16.sp, color = Color.White, text = heading, fontWeight = FontWeight.Bold)
                if (description?.isNotEmpty() == true) {
                    Text(
                        fontSize = 14.sp,
                        color = Color.White,
                        text = description
                    )
                }
            }
            Image(
                bitmap = thumbnail.asImageBitmap(),
                contentDescription = "image description",
                contentScale = ContentScale.FillBounds
            )

        }

    }
}