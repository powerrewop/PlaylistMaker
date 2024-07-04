package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val PLM_PREFERENCES_1 = "plm_preferences_1"
const val THEME_SELECT = "select_day_night_theme"
const val HISTORY_SEARCH = "user_history_search"
class App : Application() {

    var darkTheme = false
    var sharedPrefs: SharedPreferences? = null


    override fun onCreate() {
        super.onCreate()

        sharedPrefs = getSharedPreferences(PLM_PREFERENCES_1, MODE_PRIVATE)
        darkTheme = sharedPrefs!!.getBoolean(THEME_SELECT, false)
        switchTheme(darkTheme)

    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}