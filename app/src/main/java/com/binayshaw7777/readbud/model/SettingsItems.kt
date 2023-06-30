package com.binayshaw7777.readbud.model


enum class SettingsItems {
    APPEARANCE,
    STORAGE,
    ABOUT;

    companion object {
        fun getItemNameFromString(stringTypeItemName: String): SettingsItems {
            return when (stringTypeItemName) {
                "Appearance" -> {
                    APPEARANCE
                }

                "Storage" -> {
                    STORAGE
                }

                "About" -> {
                    ABOUT
                }

                else -> {
                    ABOUT
                }
            }
        }
    }
}