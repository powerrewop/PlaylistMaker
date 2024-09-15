package com.practicum.playlistmaker.domain

import com.practicum.playlistmaker.domain.storage.AppThemeStorage

class SaveAppThemeUseCase(private val appThemeStorage: AppThemeStorage) {
    fun set(isDarkTheme: Boolean){
        appThemeStorage.set(isDarkTheme)
    }
}