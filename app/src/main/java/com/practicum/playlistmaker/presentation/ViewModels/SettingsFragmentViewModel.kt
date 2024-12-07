package com.practicum.playlistmaker.presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.domain.usecase.AppThemeInteractor
import com.practicum.playlistmaker.domain.usecase.IntentInteractor

class SettingsFragmentViewModel(
    private val intentInteractor: IntentInteractor,
    private val appThemeInteractor: AppThemeInteractor
): ViewModel() {

    private var isDarkTheme = MutableLiveData(appThemeInteractor.getTheme())
    fun getIsDarkTheme(): LiveData<Boolean> {
        return isDarkTheme
    }
    fun shareClick(){
        intentInteractor.openSend()
    }
    fun supportClick(){
        intentInteractor.openSendTo()
    }
    fun allowClick(){
        intentInteractor.openView()
    }
    fun setThemeClick(checked: Boolean){
        appThemeInteractor.changeTheme(checked)
        appThemeInteractor.setTheme(checked)
        isDarkTheme.postValue(checked)
    }
}