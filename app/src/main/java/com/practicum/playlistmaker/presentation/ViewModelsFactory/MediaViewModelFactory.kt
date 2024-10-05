package com.practicum.playlistmaker.presentation.ViewModelsFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.presentation.ViewModels.MediaViewModel

class MediaViewModelFactory: ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MediaViewModel() as T
    }
}