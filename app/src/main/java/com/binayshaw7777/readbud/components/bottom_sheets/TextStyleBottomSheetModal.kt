package com.binayshaw7777.readbud.components.bottom_sheets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.readbud.R
import com.binayshaw7777.readbud.utils.getFontWeightsIntValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextStyleBottomSheetModal(
    bottomSheetState: SheetState,
    showBottomSheet: MutableState<Boolean>,
    onFontSizeChange: (TextUnit) -> Unit,
    availableTypefaces: List<SystemFontFamily>,
    onFontChange: (SystemFontFamily) -> Unit,
    availableFontWeight: List<FontWeight>,
    onFontWeightChange: (FontWeight) -> Unit
) {
    var fontSize by remember { mutableStateOf(16.sp) }
    var fontWeight by remember { mutableStateOf(FontWeight.Normal) }
    var fontFamily: SystemFontFamily by remember { mutableStateOf(FontFamily.Serif) }

    if (showBottomSheet.value) {

        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = bottomSheetState,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Font Size: ${fontSize.value.toInt()}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = fontSize.value,
                    onValueChange = { newValue ->
                        fontSize = newValue.sp
                        onFontSizeChange(fontSize)
                    },
                    valueRange = 12f..24f, // Set the range of font sizes
                    steps = 8, // Divide the range into 8 steps
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Font Weight: ${getFontWeightsIntValue(fontWeight)}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = availableFontWeight.indexOf(fontWeight).toFloat(),
                    onValueChange = { value ->
                        fontWeight = availableFontWeight[value.toInt()]
                        onFontWeightChange(fontWeight)
                    },
                    valueRange = 0f..(availableFontWeight.size - 1).toFloat()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Font family: ${fontFamily.toString().split(".")[1]}")
                LazyRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(availableTypefaces) {
                        Card(
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    fontFamily = it
                                    onFontChange(it)
                                },
                            border = BorderStroke(2.dp, Color.Gray)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.default_typeface_mock),
                                    fontFamily = it,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}