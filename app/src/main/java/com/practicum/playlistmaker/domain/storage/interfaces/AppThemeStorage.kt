package com.practicum.playlistmaker.domain.storage.interfaces

interface AppThemeStorage {
    fun get(): Boolean
    fun set(isDarkTheme: Boolean)
}