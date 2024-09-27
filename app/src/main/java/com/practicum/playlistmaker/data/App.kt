package com.practicum.playlistmaker.data

import android.app.Application
import android.content.SharedPreferences
import com.practicum.playlistmaker.creator.Creator

const val PLM_PREFERENCES_1 = "plm_preferences_1"
const val HISTORY_SEARCH = "user_history_search"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        var tempSP = getSharedPreferences(PLM_PREFERENCES_1, MODE_PRIVATE)
        sharedPrefs = tempSP

        val appThemeInteractor = Creator.getAppThemeInteractor()
        appThemeInteractor.changeTheme(appThemeInteractor.getTheme())
    }

    companion object{
        lateinit var instance: App private set
        lateinit var sharedPrefs: SharedPreferences private set
    }
}