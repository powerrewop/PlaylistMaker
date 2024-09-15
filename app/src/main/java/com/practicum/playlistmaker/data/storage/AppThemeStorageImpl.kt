package com.practicum.playlistmaker.data.storage

import android.content.SharedPreferences
import com.practicum.playlistmaker.domain.storage.AppThemeStorage

const val THEME_SELECT = "select_day_night_theme"
class AppThemeStorageImpl(private val sharedPrefs: SharedPreferences): AppThemeStorage {

    override fun get(): Boolean {
        return sharedPrefs.getBoolean(THEME_SELECT, false)
    }

    override fun set(isDarkTheme: Boolean) {
            sharedPrefs.edit()
            ?.putBoolean(THEME_SELECT, isDarkTheme)
            ?.apply()
    }
}