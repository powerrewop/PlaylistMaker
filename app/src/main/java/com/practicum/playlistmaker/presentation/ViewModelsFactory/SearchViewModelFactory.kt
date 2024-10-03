package com.practicum.playlistmaker.presentation.ViewModelsFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.presentation.ViewModels.SearchViewModel

class SearchViewModelFactory : ViewModelProvider.Factory {

    private val creator = Creator
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(creator.getHistorySearchInteractor(), creator.getLoadTracksUseCase()) as T
    }
}