package com.binayshaw7777.readbud.ui.screens.image_screens.image_listing

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.DocumentCard
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.utils.Logger
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet


@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSheetApi::class
)
@Composable
fun ImageListing(
    recognizedTextItem: RecognizedTextItem?,
    imageViewModel: ImageViewModel,
    onFabClick: () -> Unit,
    onNavigateBack: () -> Unit
) {

    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val listOfRecognizedTextItem = imageViewModel.recognizedTextItemList.observeAsState()
    val onCompleteSaveIntoDB = imageViewModel.onCompleteSaveIntoDB.observeAsState()

    if (onCompleteSaveIntoDB.value == true) {
        imageViewModel.onCompleteSaveIntoDB.postValue(false)
        onNavigateBack()
    }

    val itemNotAdded = remember {
        mutableStateOf(true)
    }
    var onClickSave by remember {
        mutableStateOf(false)
    }

    if (onClickSave) {
        imageViewModel.saveIntoDB()
        onClickSave = false
    }

    recognizedTextItem?.let {
        if (itemNotAdded.value && it.extractedText?.isNotEmpty() == true) {
            imageViewModel.addRecognizedTextItems(it)
            itemNotAdded.value = false
        }
    }

    var showSheet by remember { mutableStateOf(false) }
    val selectedItem = remember {
        mutableStateOf(RecognizedTextItem())
    }

    if (showSheet) {
        Logger.debug("Selected item is: ${selectedItem.value}")
        selectedItem.value.extractedText?.let { value ->
            ModalSheet(
                visible = showSheet,
                onVisibleChange = { showSheet = it },
                backgroundColor = MaterialTheme.colorScheme.surface
            ) {
                var updatedString by remember {
                    mutableStateOf(value)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                        .weight(0.7f)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Edit Recognized Text",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = {
                            selectedItem.value.extractedText = updatedString
                            imageViewModel.updateRecognizedItem(selectedItem.value)
                            showSheet = false
                        }) {
                            Text(text = "Save")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center, false),
                        value = updatedString,
                        onValueChange = { updatedString = it },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                    )
                }
            }
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
                    actions = {
                        if (listOfRecognizedTextItem.value.isNullOrEmpty().not()) {
                            IconButton(onClick = { onClickSave = true }) {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Save into Database"
                                )
                            }
                        }
                    }
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
                                        onClick = {
                                            selectedItem.value = item
                                            showSheet = true
                                        },
                                        thumbnail = it1,
                                        heading = "Scan no. $index",
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



