package com.binayshaw7777.readbud.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.ui.theme.SecondarySurface

@Preview(showBackground = true)
@Composable
fun DocumentCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{ },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(SecondarySurface),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(fontSize = 16.sp, color = Color.White, text = "Scan-2023/06...", fontWeight = FontWeight.Bold)
                Text(fontSize = 14.sp, color = Color.White, text = "Description of the card item")
            }

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "image description",
                contentScale = ContentScale.FillBounds
            )

        }

    }
}