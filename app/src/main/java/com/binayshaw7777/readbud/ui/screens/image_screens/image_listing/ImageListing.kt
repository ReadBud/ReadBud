package com.binayshaw7777.readbud.ui.screens.image_screens.image_listing

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.DocumentCard
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageListing(navController: NavController) {

    val imageViewModel = remember { ImageViewModel() }
    val context = LocalContext.current
    val bitmapList by imageViewModel.bitmapList.observeAsState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
            it?.let {
                imageViewModel.addBitmap(it)
            }
        }

    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val permissionGranted = remember { mutableStateOf(false) }

    if (permissionGranted.value) {
        permissionGranted.value = false
        launcher.launch(null)
    }

    ReadBudTheme(dynamicColor = true) {

        Scaffold(Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.select_images),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = {
                        if (cameraPermissionState.hasPermission) {
                            permissionGranted.value = true
                        } else {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_fab),
                    )
                }
            }) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    bitmapList?.let { bitmaps ->
                        LazyColumn {
                            itemsIndexed(bitmaps) { index, bitmap ->
                                DocumentCard(
                                    thumbnail = bitmap,
                                    heading = "Bitmap item: $index",
                                    description = null
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ImageListing(rememberNavController())
}



