package com.practicum.playlistmaker.presentation.ViewModelsFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.presentation.ViewModels.SettingsViewModel

class SettingsViewModelFactory : ViewModelProvider.Factory {

    private val creator = Creator
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(creator.getIntentInteractor(), creator.getAppThemeInteractor()) as T
    }
}