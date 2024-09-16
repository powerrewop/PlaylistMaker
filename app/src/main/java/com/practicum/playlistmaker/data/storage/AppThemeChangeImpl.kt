package com.practicum.playlistmaker.data.storage

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeChange

class AppThemeChangeImpl: AppThemeChange {
    override fun change(isDarkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}