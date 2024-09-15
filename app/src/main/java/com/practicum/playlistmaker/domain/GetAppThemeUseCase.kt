package com.practicum.playlistmaker.domain

import com.practicum.playlistmaker.domain.storage.AppThemeStorage

class GetAppThemeUseCase(private val appThemeStorage: AppThemeStorage) {
    fun get(): Boolean{
        return appThemeStorage.get()
    }
}