package com.practicum.playlistmaker.domain.storage

interface AppThemeStorage {
    fun get(): Boolean
    fun set(isDarkTheme: Boolean)
}