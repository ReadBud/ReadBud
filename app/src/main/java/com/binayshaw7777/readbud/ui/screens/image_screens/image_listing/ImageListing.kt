package com.binayshaw7777.readbud.ui.screens.image_screens.image_listing

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.binayshaw7777.readbud.components.BottomSheet
import com.binayshaw7777.readbud.components.ImageSourceOptions
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.theme.PrimaryContainer
import com.binayshaw7777.readbud.utils.Logger
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageListing(navController: NavController) {

    val imageViewModel = remember { ImageViewModel() }

    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
            it?.let {
                bitmap = it
                Logger.debug(it.toString())
            }
        }

    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val noPermissionGranted = remember { mutableStateOf(false) }
    val permissionGranted = remember { mutableStateOf(false) }

//    val showSheet = remember { mutableStateOf(false) }
//    val functionVariable: @Composable () -> Unit = { ImageSourceOptions() }

    bitmap?.let {

    }

    if (permissionGranted.value) {
        permissionGranted.value = false
        launcher.launch(null)
    } else if (noPermissionGranted.value) {
        cameraPermissionState.launchPermissionRequest()
        noPermissionGranted.value = false
    }

    Scaffold(Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(20.dp),
                onClick = {
                    if (cameraPermissionState.hasPermission) {
                        permissionGranted.value = true
                    } else {
                        noPermissionGranted.value = true
                    }
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
        }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ImageListing(rememberNavController())
}



