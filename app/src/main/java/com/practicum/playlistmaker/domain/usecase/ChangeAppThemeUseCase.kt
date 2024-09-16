package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeChange


class ChangeAppThemeUseCase(private val appThemeChange: AppThemeChange) {
    fun change(isDarkTheme: Boolean){
       appThemeChange.change(isDarkTheme)
    }
}