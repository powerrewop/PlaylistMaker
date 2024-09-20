package com.practicum.playlistmaker.creator

import android.content.Context
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.storage.AppThemeChangeImpl
import com.practicum.playlistmaker.data.storage.AppThemeStorageImpl
import com.practicum.playlistmaker.data.storage.IntentUserImpl
import com.practicum.playlistmaker.domain.usecase.ChangeAppThemeUseCase
import com.practicum.playlistmaker.domain.usecase.GetAppThemeUseCase
import com.practicum.playlistmaker.domain.usecase.GetIntentsUsaCase
import com.practicum.playlistmaker.domain.usecase.SaveAppThemeUseCase
import com.practicum.playlistmaker.domain.storage.interfaces.IntentUser

class SettingsUseCaseCreator(private val context: Context) {

    private val appThemeStorage = AppThemeStorageImpl((context.applicationContext as App).sharedPrefs!!)
    fun getSelectIntentUser(): IntentUser {
        val intentUser = IntentUserImpl(context = context)
        val getIntentUserCase = GetIntentsUsaCase(intentUser)
        return getIntentUserCase.getUserIntent()
    }

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