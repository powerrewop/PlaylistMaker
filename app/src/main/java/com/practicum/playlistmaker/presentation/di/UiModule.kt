package com.practicum.playlistmaker.presentation.di

import com.practicum.playlistmaker.presentation.ViewModels.MainViewModel
import com.practicum.playlistmaker.presentation.ViewModels.MediaViewModel
import com.practicum.playlistmaker.presentation.ViewModels.PlayerViewModel
import com.practicum.playlistmaker.presentation.ViewModels.SearchViewModel
import com.practicum.playlistmaker.presentation.ViewModels.SettingsViewModel
import org.koin.dsl.module

val uiModule = module {

    factory<MainViewModel> {
        MainViewModel(get())
    }

    factory<MediaViewModel> {
        MediaViewModel()
    }

    factory<PlayerViewModel> {(jsonTrack: String) ->
        PlayerViewModel(get(), get(), jsonTrack)
    }

    factory<SearchViewModel> {
        SearchViewModel(get(), get())
    }

    factory<SettingsViewModel> {
        SettingsViewModel(get(), get())
    }
}