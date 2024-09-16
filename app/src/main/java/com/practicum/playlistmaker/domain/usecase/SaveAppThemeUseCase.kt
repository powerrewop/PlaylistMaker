package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeStorage

class SaveAppThemeUseCase(private val appThemeStorage: AppThemeStorage) {
    fun set(isDarkTheme: Boolean){
        appThemeStorage.set(isDarkTheme)
    }
}