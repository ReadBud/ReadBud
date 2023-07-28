package com.binayshaw7777.readbud.model

/**
 * This code was reused from this blog: https://medium.com/@cherfaoui_dev/handle-runtime-permissions-in-jetpack-compose-fc678b8b4a62
 * */

enum class NeededPermission(
    val permission: String,
    val title: String,
    val description: String,
    val permanentlyDeniedDescription: String,
) {

    CAMERA_PERMISSION(
        permission = android.Manifest.permission.CAMERA,
        title = "Camera Permission",
        description = "This permission is needed to access your camera. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to access your camera. Please grant the permission in app settings."
    );

    fun permissionTextProvider(isPermanentDenied: Boolean): String {
        return if (isPermanentDenied) this.permanentlyDeniedDescription else this.description
    }
}

fun getNeededPermission(permission: String): NeededPermission {
    return NeededPermission.values().find { it.permission == permission } ?: throw IllegalArgumentException("Permission $permission is not supported")
}