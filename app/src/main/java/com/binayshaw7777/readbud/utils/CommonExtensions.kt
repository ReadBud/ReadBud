package com.binayshaw7777.readbud.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.SystemFontFamily
import com.binayshaw7777.readbud.ui.screens.MainActivity

fun Activity.goToAppSetting() {
    val intentToAppSettings = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(intentToAppSettings)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.closeApp() {
    (this as? MainActivity)?.finish()
}

fun getFontWeights(): List<FontWeight> {
    return listOf(
        FontWeight.ExtraLight,
        FontWeight.Thin,
        FontWeight.Normal,
        FontWeight.Medium,
        FontWeight.SemiBold,
        FontWeight.Bold,
        FontWeight.ExtraBold,
        FontWeight.Black
    )
}

fun getFontWeightsIntValue(fontWeight: FontWeight): String {
    return when (fontWeight.weight) {
        100 -> "Extra Light"
        200 -> "Light"
        300 -> "Normal"
        400 -> "Medium"
        500 -> "Semi Bold"
        600 -> "Bold"
        700 -> "Extra Bold"
        else -> {
            "Black"
        }
    }
}

fun getTypeFaces(): List<SystemFontFamily> {
    return listOf(
        FontFamily.Default,
        FontFamily.Serif,
        FontFamily.SansSerif,
        FontFamily.Cursive,
        FontFamily.Monospace
    )
}