package com.binayshaw7777.readbud.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.model.NeededPermission

@Composable
fun PermissionAlertDialog(
    neededPermission: NeededPermission,
    isPermissionDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isPermissionDeclined)
                        onGoToAppSettingsClick()
                    else
                        onOkClick()
                }
            ) {
                Text(
                    text = if (isPermissionDeclined)
                        stringResource(R.string.go_to_app_setting)
                    else
                        stringResource(R.string.ok)
                )
            }
        },
        title = {
            Text(
                text = neededPermission.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Text(
                text = neededPermission.permissionTextProvider(isPermissionDeclined),
            )
        }
    )
}