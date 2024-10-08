package com.practicum.playlistmaker.data

import android.app.Application
import android.content.SharedPreferences
import com.practicum.playlistmaker.data.di.storageModule
import com.practicum.playlistmaker.domain.di.usecasesModule
import com.practicum.playlistmaker.domain.usecase.AppThemeInteractor
import com.practicum.playlistmaker.presentation.di.uiModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

const val PLM_PREFERENCES_1 = "plm_preferences_1"
const val HISTORY_SEARCH = "user_history_search"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(storageModule, usecasesModule, uiModule)
        }

        instance = this

        val tempSP: SharedPreferences by inject()
        sharedPrefs = tempSP

        val appThemeInteractor: AppThemeInteractor by inject()
        appThemeInteractor.changeTheme(appThemeInteractor.getTheme())
    }

    companion object{
        lateinit var instance: App private set
        lateinit var sharedPrefs: SharedPreferences private set
    }
}