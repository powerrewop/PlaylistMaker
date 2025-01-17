package com.practicum.playlistmaker.domain.di

import com.practicum.playlistmaker.domain.db.FavTrackInteractorImpl
import com.practicum.playlistmaker.domain.usecase.AppThemeInteractor
import com.practicum.playlistmaker.domain.usecase.FavTrackInteractor
import com.practicum.playlistmaker.domain.usecase.HistorySearchInteractor
import com.practicum.playlistmaker.domain.usecase.IntentInteractor
import com.practicum.playlistmaker.domain.usecase.LoadTracksUseCase
import com.practicum.playlistmaker.domain.usecase.MediaplayerUseCase
import com.practicum.playlistmaker.domain.usecase.ParamDataUseCase
import org.koin.dsl.module

val usecasesModule = module {
    factory<AppThemeInteractor> {
        AppThemeInteractor(get())
    }

    factory<HistorySearchInteractor> {
        HistorySearchInteractor(get())
    }

    factory<IntentInteractor> {
        IntentInteractor(get())
    }

    factory<LoadTracksUseCase> {
        LoadTracksUseCase(get())
    }

    factory<MediaplayerUseCase> {
        MediaplayerUseCase(get())
    }

    factory<ParamDataUseCase> {
        ParamDataUseCase(get())
    }

    single <FavTrackInteractor> {
        FavTrackInteractorImpl(get())
    }
}