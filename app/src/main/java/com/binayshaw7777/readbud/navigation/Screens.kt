package com.binayshaw7777.readbud.navigation

import com.binayshaw7777.readbud.utils.Constants


sealed class Screens(val name: String) {
    object Home : Screens(Constants.HOME)
    object ImageListing : Screens(Constants.IMAGE_LISTING)
    object MLKitTextRecognition : Screens(Constants.ML_KIT_RECOGNITION)
    object Settings : Screens(Constants.SETTINGS)
    object BookView : Screens(Constants.BOOK_VIEW)
}
