package com.practicum.playlistmaker.domain

import com.practicum.playlistmaker.domain.storage.AppThemeChange


class ChangeAppThemeUseCase(private val appThemeChange: AppThemeChange) {
    fun change(isDarkTheme: Boolean){
       appThemeChange.change(isDarkTheme)
    }
}