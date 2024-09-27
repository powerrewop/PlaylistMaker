package com.practicum.playlistmaker.data.storage

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.data.App

const val THEME_SELECT = "select_day_night_theme"
class AppTheme {
     fun changeTheme(isDarkTheme: Boolean) {
         AppCompatDelegate.setDefaultNightMode(
             if (isDarkTheme) {
                 AppCompatDelegate.MODE_NIGHT_YES
             } else {
                 AppCompatDelegate.MODE_NIGHT_NO
             }
         )
    }
     fun getTheme(): Boolean {
         return App.sharedPrefs.getBoolean(THEME_SELECT, false)
    }

     fun setTheme(isDarkTheme: Boolean) {
         App.sharedPrefs.edit()
             ?.putBoolean(THEME_SELECT, isDarkTheme)
             ?.apply()
    }
}