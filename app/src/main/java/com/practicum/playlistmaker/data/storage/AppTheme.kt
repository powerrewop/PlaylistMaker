package com.practicum.playlistmaker.data.storage

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val THEME_SELECT = "select_day_night_theme"
class AppTheme(private val sharedPref: SharedPreferences) {
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
         return sharedPref.getBoolean(THEME_SELECT, false)
    }

     fun setTheme(isDarkTheme: Boolean) {
         sharedPref.edit()
             ?.putBoolean(THEME_SELECT, isDarkTheme)
             ?.apply()
    }
}