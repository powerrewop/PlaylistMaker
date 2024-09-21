package com.practicum.playlistmaker.creator

import android.content.Context
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.storage.AppThemeChangeImpl
import com.practicum.playlistmaker.data.storage.AppThemeStorageImpl
import com.practicum.playlistmaker.domain.usecase.ChangeAppThemeUseCase
import com.practicum.playlistmaker.domain.usecase.GetAppThemeUseCase
import com.practicum.playlistmaker.domain.usecase.SaveAppThemeUseCase

class SettingsUseCaseCreator(private val context: Context) {

    private val appThemeStorage = AppThemeStorageImpl((context.applicationContext as App).sharedPrefs!!)

    fun getAppThemeUseCase(): GetAppThemeUseCase {
        return GetAppThemeUseCase(appThemeStorage)
    }

    fun getSaveAppThemeUseCase(): SaveAppThemeUseCase {
        return SaveAppThemeUseCase(appThemeStorage)
    }

    fun getChangeAppThemeUseCase(): ChangeAppThemeUseCase {
        val appThemeChange = AppThemeChangeImpl()
        return ChangeAppThemeUseCase(appThemeChange)
    }
}