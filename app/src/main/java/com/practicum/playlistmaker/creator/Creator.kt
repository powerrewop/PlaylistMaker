package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.impl.AppThemeRepositoryImpl
import com.practicum.playlistmaker.data.impl.HistorySearchRepositoryImpl
import com.practicum.playlistmaker.data.impl.IntentRepositoryImpl
import com.practicum.playlistmaker.data.impl.MediaplayerRepositoryImpl
import com.practicum.playlistmaker.data.impl.ParamDataRepositoryImpl
import com.practicum.playlistmaker.data.impl.SearchRepositoryImpl
import com.practicum.playlistmaker.data.storage.AppTheme
import com.practicum.playlistmaker.data.storage.IntentWork
import com.practicum.playlistmaker.data.storage.MediaPlayerNew
import com.practicum.playlistmaker.data.storage.NetworkClient
import com.practicum.playlistmaker.data.storage.ParamData
import com.practicum.playlistmaker.data.storage.SearchHistory
import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeRepository
import com.practicum.playlistmaker.domain.storage.interfaces.HistorySearchRepository
import com.practicum.playlistmaker.domain.storage.interfaces.IntentRepository
import com.practicum.playlistmaker.domain.storage.interfaces.MediaPlayerRepository
import com.practicum.playlistmaker.domain.storage.interfaces.ParamDataRepository
import com.practicum.playlistmaker.domain.storage.interfaces.SearchRepository
import com.practicum.playlistmaker.domain.usecase.AppThemeInteractor
import com.practicum.playlistmaker.domain.usecase.HistorySearchInteractor
import com.practicum.playlistmaker.domain.usecase.IntentInteractor
import com.practicum.playlistmaker.domain.usecase.LoadTracksUseCase
import com.practicum.playlistmaker.domain.usecase.MediaplayerUseCase
import com.practicum.playlistmaker.domain.usecase.ParamDataUseCase

object Creator {
    private fun getAppTheme(): AppTheme{
        return AppTheme()
    }
    private fun getAppThemeRepository(): AppThemeRepository{
        return AppThemeRepositoryImpl(AppTheme())
    }
    fun getAppThemeInteractor(): AppThemeInteractor{
            return AppThemeInteractor(getAppThemeRepository())
    }
    ////////////////////////////
    private fun getIntentWork(): IntentWork {
        return IntentWork()
    }
    private fun getIntentRepository(): IntentRepository {
        return IntentRepositoryImpl(IntentWork())
    }
    fun getIntentInteractor(): IntentInteractor {
        return IntentInteractor(getIntentRepository())
    }
    ////////////////////////////

    private fun getParamData(): ParamData {
        return ParamData()
    }
    private fun getParamDataRepository(): ParamDataRepository {
        return ParamDataRepositoryImpl(getParamData())
    }
    fun getParamDataUseCase(): ParamDataUseCase {
        return ParamDataUseCase(getParamDataRepository())
    }
    ////////////////////////////////

    private fun getMediaPlayerNew(): MediaPlayerNew {
        return MediaPlayerNew()
    }
    private fun getMediaplayerRepository(): MediaPlayerRepository {
        return MediaplayerRepositoryImpl(MediaPlayerNew())
    }
    fun getMediaplayerUseCase(): MediaplayerUseCase {
        return MediaplayerUseCase(getMediaplayerRepository())
    }
    /////////////////////////////////
    private fun getSearchHistory(): SearchHistory {
        return SearchHistory()
    }
    private fun getHistorySearchRepository(): HistorySearchRepository {
        return HistorySearchRepositoryImpl(getSearchHistory())
    }
    fun getHistorySearchInteractor(): HistorySearchInteractor {
        return HistorySearchInteractor(getHistorySearchRepository())
    }
    //////////////////////////////
    private fun getNetworkClient(): NetworkClient {
        return NetworkClient("https://itunes.apple.com")
    }
    private fun getSearchRepository(): SearchRepository {
        return SearchRepositoryImpl(getNetworkClient())
    }
    fun getLoadTracksUseCase(): LoadTracksUseCase {
        return LoadTracksUseCase(getSearchRepository())
    }

}