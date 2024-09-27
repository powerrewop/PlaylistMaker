package com.practicum.playlistmaker.domain.storage.interfaces

interface AppThemeRepository {
    fun changeTheme(isDarkTheme: Boolean)
    fun getTheme(): Boolean
    fun setTheme(isDarkTheme: Boolean)
}