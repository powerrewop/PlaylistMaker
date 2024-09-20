package com.practicum.playlistmaker.data

import android.app.Application
import android.content.SharedPreferences
import com.practicum.playlistmaker.data.storage.AppThemeChangeImpl
import com.practicum.playlistmaker.data.storage.AppThemeStorageImpl
import com.practicum.playlistmaker.domain.usecase.ChangeAppThemeUseCase
import com.practicum.playlistmaker.domain.usecase.GetAppThemeUseCase

const val PLM_PREFERENCES_1 = "plm_preferences_1"
const val HISTORY_SEARCH = "user_history_search"

class App : Application() {

    var sharedPrefs: SharedPreferences? = null

    override fun onCreate() {
        super.onCreate()

        var tempSP = getSharedPreferences(PLM_PREFERENCES_1, MODE_PRIVATE)
        sharedPrefs = tempSP
        val appThemeStorage = AppThemeStorageImpl(tempSP)
        val getAppThemeUseCase = GetAppThemeUseCase(appThemeStorage)

        val appThemeChange = AppThemeChangeImpl()
        val changeAppThemeUseCase = ChangeAppThemeUseCase(appThemeChange)
        changeAppThemeUseCase.change(getAppThemeUseCase.get())
    }
}