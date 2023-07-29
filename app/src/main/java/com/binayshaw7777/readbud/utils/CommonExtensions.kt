package com.binayshaw7777.readbud.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
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