package com.binayshaw7777.readbud.ui.screens.image_screens.image_listing

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.binayshaw7777.readbud.data.viewmodel.ScansViewModel
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.navigation.Screens
import com.binayshaw7777.readbud.ui.screens.image_screens.ImageViewModel
import com.binayshaw7777.readbud.ui.theme.ReadBudTheme
import com.binayshaw7777.readbud.utils.Logger
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageListing(
    imageViewModel: ImageViewModel,
    scansViewModel: ScansViewModel,
    navController: NavController
) {

    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val listOfRecognizedTextItem = imageViewModel.recognizedTextItemList.observeAsState()
    val onSaveObserverState = scansViewModel.onCompleteSaveIntoDB.observeAsState()

    var onStartProgress by remember { mutableStateOf(false) }

    if (onSaveObserverState.value == true) {
        scansViewModel.onCompleteSaveIntoDB.postValue(false)
        onStartProgress = false
        navController.navigateUp()
    }
    var onClickSave by remember {
        mutableStateOf(false)
    }
    var onItemClickListener by remember { mutableStateOf(false) }

    val selectedItem = remember {
        mutableStateOf(RecognizedTextItem())
    }

    if (onClickSave) {
        ModalBottomSheet(
            onDismissRequest = { onClickSave = false }
        ) {

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
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        onClickSave = false
                    }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Button(onClick = {
                        scansViewModel.saveIntoDB(
                            saveAsFileName,
                            imageViewModel.clearAllRecognizedTextItems()
                        )
                        onClickSave = false
                    }) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }

    if (onItemClickListener) {
        Logger.debug("Selected item is: ${selectedItem.value}")
        selectedItem.value.extractedText?.let { value ->

            ModalBottomSheet(
                onDismissRequest = { onItemClickListener = false }
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
                            selectedItem.value.extractedText = updatedString
                            imageViewModel.updateRecognizedItem(selectedItem.value)
                            onStartProgress = true
                            onItemClickListener = false
                        }) {
                            Text(text = stringResource(id = R.string.save))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
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
        BackHandler(true) {
            imageViewModel.clearAllRecognizedTextItems()
            navController.popBackStack()
        }
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
                            IconButton(onClick = {
                                onClickSave = true
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = stringResource(R.string.save_into_database)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = {
                        if (cameraPermissionState.hasPermission) {
                            navController.navigate(Screens.MLKitTextRecognition.name)
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
            }) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    if (onStartProgress) {
                        CircularProgressIndicator()
                    }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        listOfRecognizedTextItem.value?.let {
                            LazyColumn {
                                itemsIndexed(it) { index, item ->
                                    item.thumbnail?.let { it1 ->
                                        DocumentCard(
                                            onClick = {
                                                selectedItem.value = item
                                                onItemClickListener = true
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
}



