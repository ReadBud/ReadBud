package com.binayshaw7777.readbud.ui.screens.image_screens.image_listing

import android.Manifest
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.DocumentCard
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.utils.Logger
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageListing(
    recognizedTextItem: RecognizedTextItem?,
    imageViewModel: ImageViewModel,
    onFabClick: () -> Unit
) {

    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val listOfRecognizedTextItem = imageViewModel.recognizedTextItemList.observeAsState()
    val itemNotAdded = remember {
        mutableStateOf(true)
    }

    recognizedTextItem?.let {
        if (itemNotAdded.value && it.extractedText?.isNotEmpty() == true) {
            imageViewModel.addRecognizedTextItems(it)
            itemNotAdded.value = false
        }
    }

    ReadBudTheme(dynamicColor = true) {

        Scaffold(
            Modifier.fillMaxSize(),
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
                            onFabClick()
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
                    listOfRecognizedTextItem.value?.let {
                        LazyColumn {

                            itemsIndexed(it) { index, item ->
                                item.thumbnail?.let { it1 ->
                                    DocumentCard(
                                        thumbnail = it1,
                                        heading = "Item no. $index",
                                        description = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    ImageListing(RecognizedTextItem(), ImageViewModel()) {}
//}



