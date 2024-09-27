package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeRepository

class AppThemeInteractor(private val appThemeRepository: AppThemeRepository) {
    fun changeTheme(isDarkTheme: Boolean){
        appThemeRepository.changeTheme(isDarkTheme)
    }
    fun getTheme(): Boolean{
       return appThemeRepository.getTheme()
    }
    fun setTheme(isDarkTheme: Boolean){
        appThemeRepository.setTheme(isDarkTheme)
    }
}