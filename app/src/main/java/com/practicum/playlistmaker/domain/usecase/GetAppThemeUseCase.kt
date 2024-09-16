package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeStorage

class GetAppThemeUseCase(private val appThemeStorage: AppThemeStorage) {
    fun get(): Boolean{
        return appThemeStorage.get()
    }
}