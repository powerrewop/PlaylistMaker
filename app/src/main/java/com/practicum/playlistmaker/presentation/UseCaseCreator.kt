package com.practicum.playlistmaker.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.data.storage.AppThemeChangeImpl
import com.practicum.playlistmaker.data.storage.AppThemeStorageImpl
import com.practicum.playlistmaker.data.storage.IntentUserImpl
import com.practicum.playlistmaker.domain.ChangeAppThemeUseCase
import com.practicum.playlistmaker.domain.GetAppThemeUseCase
import com.practicum.playlistmaker.domain.GetIntentsUsaCase
import com.practicum.playlistmaker.domain.SaveAppThemeUseCase
import com.practicum.playlistmaker.domain.storage.IntentUser

const val PLM_PREFERENCES_1 = "plm_preferences_1"

class UseCaseCreator(private val context: Context) {

    private val appThemeStorage = AppThemeStorageImpl(context.getSharedPreferences(PLM_PREFERENCES_1, AppCompatActivity.MODE_PRIVATE))
    fun getSelectIntentUser(): IntentUser{
        val intentUser = IntentUserImpl(context = context)
        val getIntentUserCase = GetIntentsUsaCase(intentUser)
        return getIntentUserCase.getUserIntent()
    }

    fun getAppThemeUseCase(): GetAppThemeUseCase{
        return GetAppThemeUseCase(appThemeStorage)
    }

    fun getSaveAppThemeUseCase(): SaveAppThemeUseCase{
        return SaveAppThemeUseCase(appThemeStorage)
    }

    fun getChangeAppThemeUseCase(): ChangeAppThemeUseCase{
        val appThemeChange = AppThemeChangeImpl()
        return ChangeAppThemeUseCase(appThemeChange)
    }
}