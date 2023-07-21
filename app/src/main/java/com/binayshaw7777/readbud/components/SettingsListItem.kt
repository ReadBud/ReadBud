package com.binayshaw7777.readbud.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun SettingsListItem(
    icon: Int,
    title: String,
    showThemeSwitch: Boolean,
    onClickSettingsItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClickSettingsItem() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(title)
        }
        Row {
            if (showThemeSwitch) { ThemeSwitch() }
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}