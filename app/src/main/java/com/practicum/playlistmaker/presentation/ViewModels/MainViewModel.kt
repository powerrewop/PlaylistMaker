package com.practicum.playlistmaker.presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.domain.usecase.IntentInteractor

class MainViewModel(private val intentInteractor: IntentInteractor): ViewModel() {

    fun openSearch(){
        intentInteractor.openSearch()
    }
    fun openMedia() {
        intentInteractor.openMedia()
    }
    fun openSettings(){
        intentInteractor.openSettings()
    }

}