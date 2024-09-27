package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.storage.AppTheme
import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeRepository

class AppThemeRepositoryImpl(private val appTheme: AppTheme): AppThemeRepository {
    override fun changeTheme(isDarkTheme: Boolean) {
        appTheme.changeTheme(isDarkTheme)
    }

    override fun getTheme(): Boolean {
        return appTheme.getTheme()
    }

    override fun setTheme(isDarkTheme: Boolean) {
        appTheme.setTheme(isDarkTheme)
    }
}