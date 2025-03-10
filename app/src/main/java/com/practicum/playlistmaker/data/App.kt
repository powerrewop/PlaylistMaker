package com.practicum.playlistmaker.data

import android.app.Application
import com.markodevcic.peko.PermissionRequester
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

        val appThemeInteractor: AppThemeInteractor by inject()
        appThemeInteractor.changeTheme(appThemeInteractor.getTheme())

        PermissionRequester.initialize(applicationContext)
    }
}