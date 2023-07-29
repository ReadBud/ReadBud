package com.binayshaw7777.readbud.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Activity.goToAppSetting() {
    val intentToAppSettings = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(intentToAppSettings)
}