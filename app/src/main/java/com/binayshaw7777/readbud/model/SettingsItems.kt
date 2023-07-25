package com.binayshaw7777.readbud.model


enum class SettingsItems {
    APPEARANCE,
    NEED_HELP,
    SHARE,
    RATE,
    ABOUT;

    companion object {
        fun getItemNameFromString(stringTypeItemName: String): SettingsItems {
            return when (stringTypeItemName) {
                "Appearance" -> {
                    APPEARANCE
                }
                "Need Help?" -> {
                    NEED_HELP
                }

                "Share" -> {
                    SHARE
                }

                "Rate" -> {
                    RATE
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