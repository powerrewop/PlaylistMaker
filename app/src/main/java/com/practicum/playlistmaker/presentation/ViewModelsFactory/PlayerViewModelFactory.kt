package com.practicum.playlistmaker.presentation.ViewModelsFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.presentation.ViewModels.PlayerViewModel

class PlayerViewModelFactory(private val jsonTrack: String?) : ViewModelProvider.Factory {
    private val creator = Creator
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(creator.getParamDataUseCase(), creator.getMediaplayerUseCase(), jsonTrack) as T
    }
}