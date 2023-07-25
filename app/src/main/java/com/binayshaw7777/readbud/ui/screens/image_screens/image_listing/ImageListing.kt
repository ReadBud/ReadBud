package com.binayshaw7777.readbud.ui.screens.image_screens.image_listing

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.components.DocumentCard
import com.binayshaw7777.readbud.components.EmptyState
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.navigation.Screens
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageListing(
    imageViewModel: ImageViewModel, scansViewModel: ScansViewModel, navController: NavController
) {

    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val listOfRecognizedTextItem = imageViewModel.recognizedTextItemList.observeAsState()
    val onSaveObserverState = scansViewModel.onCompleteSaveIntoDB.observeAsState()

    val scope = rememberCoroutineScope()

    val onClickSave = remember { mutableStateOf(false) }
    val onItemClickListener = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(RecognizedTextItem()) }

    if (onSaveObserverState.value == true) {
        scansViewModel.onCompleteSaveIntoDB.postValue(false)
        navController.navigateUp()
    }

    if (onClickSave.value) {
        ShowBottomSheetForSaving(onClickSave, scansViewModel, imageViewModel)
    }

    if (onItemClickListener.value) {
        ShowBottomSheetOnItemClick(
            onItemClickListener,
            selectedItem,
            imageViewModel
        )
    }

    BackHandler(true) {
        onBackPressHandler(imageViewModel, navController)
    }
    Scaffold(Modifier.fillMaxSize(),

        topBar = {
            ShowTopAppBar(
                listOfRecognizedTextItem,
                onClickSave,
                navController,
                imageViewModel
            )
        },

        floatingActionButton = {
            ShowFloatingActionButton(navController, cameraPermissionState, scope)
        }

    ) { padding ->

        Surface(modifier = Modifier.padding(padding)) {
            Column {
                if (listOfRecognizedTextItem.value?.isNotEmpty() == true) {

                    LazyColumn {
                        itemsIndexed(listOfRecognizedTextItem.value!!) { _, item ->
                            item.thumbnail?.let {
                                OnSwipeList(
                                    item = item,
                                    onItemClicked = {
                                        selectedItem.value = item
                                        onItemClickListener.value = true
                                    },
                                    imageViewModel = imageViewModel
                                )
                            }
                        }
                    }
                } else {
                    EmptyState(
                        contentDescription = stringResource(id = R.string.no_scans_added),
                        message = stringResource(
                            id = R.string.no_saved_scans_click_on_the_camera_button_to_scan
                        )
                    )
                }
            }
        }
    }
}

private fun onBackPressHandler(imageViewModel: ImageViewModel, navController: NavController) {
    imageViewModel.clearAllRecognizedTextItems()
    navController.popBackStack()
}

@Composable
fun OnSwipeList(
    item: RecognizedTextItem,
    onItemClicked: (itemId: Int) -> Unit,
    imageViewModel: ImageViewModel
) {

    val showDeleteItemDialog = remember { mutableStateOf(false) }

    val deleteAction = SwipeAction(
        onSwipe = {
            showDeleteItemDialog.value = true
        },
        icon = {
            Icon(
                modifier = Modifier.padding(12.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White
            )
        },
        background = Color.Red,
    )

    if (showDeleteItemDialog.value) {
        ShowDeleteItemDialog(item, showDeleteItemDialog, imageViewModel)
    }

    SwipeableActionsBox(
        swipeThreshold = 100.dp,
        endActions = listOf(deleteAction)
    ) {

        DocumentCard(
            onClick = {
                onItemClicked(item.index)
            },
            thumbnail = item.thumbnail!!,
            heading = "Scan no. ${item.index}",
            description = ""
        )
    }
}

@Composable
fun ShowDeleteItemDialog(
    item: RecognizedTextItem,
    showDeleteItemDialog: MutableState<Boolean>,
    imageViewModel: ImageViewModel
) {
    AlertDialog(
        onDismissRequest = {
            showDeleteItemDialog.value = false
        },
        icon = { Icon(Icons.Filled.Delete, contentDescription = null) },
        title = {
            Text(text = stringResource(R.string.delete_item))
        },
        text = {
            Text(
                stringResource(R.string.do_you_want_to_this_scan_permanently)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    imageViewModel.removeItemFromIndex(item.index)
                    showDeleteItemDialog.value = false
                }
            ) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDeleteItemDialog.value = false
                }
            ) {
                Text(stringResource(R.string.don_t_delete))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowTopAppBar(
    listOfRecognizedTextItem: State<List<RecognizedTextItem>?>,
    onClickSave: MutableState<Boolean>,
    navController: NavController,
    imageViewModel: ImageViewModel
) {
    CenterAlignedTopAppBar(title = {
        Text(
            stringResource(id = R.string.select_images),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton(onClick = {
            onBackPressHandler(imageViewModel, navController)
        }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.go_back)
            )
        }
    }, actions = {
        if (listOfRecognizedTextItem.value.isNullOrEmpty().not()) {
            IconButton(onClick = {
                onClickSave.value = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(R.string.save_into_database)
                )
            }
        }
    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent
    )
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowFloatingActionButton(
    navController: NavController, cameraPermissionState: PermissionState, scope: CoroutineScope
) {
    FloatingActionButton(
        modifier = Modifier.padding(20.dp),
        onClick = {
            if (cameraPermissionState.hasPermission) {
                scope.launch {
                    navController.navigate(Screens.MLKitTextRecognition.name)
                }
            } else {
                cameraPermissionState.launchPermissionRequest()
            }
        },
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.camera_icon),
            contentDescription = stringResource(R.string.add_fab),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheetOnItemClick(
    onItemClickListener: MutableState<Boolean>,
    selectedItem: MutableState<RecognizedTextItem>,
    imageViewModel: ImageViewModel
) {

    selectedItem.value.extractedText?.let { value ->
        ModalBottomSheet(onDismissRequest = { onItemClickListener.value = false }) {

            var updatedRecognizedTextValue by remember {
                mutableStateOf(value)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.edit_recognized_text),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = {
                        selectedItem.value.extractedText = updatedRecognizedTextValue
                        imageViewModel.updateRecognizedItem(selectedItem.value)
                        onItemClickListener.value = false
                    }) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = updatedRecognizedTextValue,
                    onValueChange = { updatedRecognizedTextValue = it },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheetForSaving(
    onClickSave: MutableState<Boolean>,
    scansViewModel: ScansViewModel,
    imageViewModel: ImageViewModel
) {
    ModalBottomSheet(onDismissRequest = { onClickSave.value = false }) {

        var saveAsFileName by remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.save_file_name_as),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = saveAsFileName,
                onValueChange = { saveAsFileName = it },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    onClickSave.value = false
                }) {
                    Text(text = stringResource(R.string.cancel))
                }
                Button(onClick = {
                    scansViewModel.saveIntoDB(
                        saveAsFileName, imageViewModel.clearAllRecognizedTextItems()
                    )
                    onClickSave.value = false
                }) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}
