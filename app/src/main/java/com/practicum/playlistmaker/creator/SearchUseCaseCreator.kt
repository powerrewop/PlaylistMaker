package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.storage.ClearHistoryImpl
import com.practicum.playlistmaker.data.storage.HistorySearchImpl
import com.practicum.playlistmaker.data.storage.RunSearchImpl
import com.practicum.playlistmaker.data.storage.UserTextWorkImpl
import com.practicum.playlistmaker.domain.usecase.ClearHistoryUseCase
import com.practicum.playlistmaker.domain.usecase.GetHistorySearchUseCase
import com.practicum.playlistmaker.domain.usecase.GetSearchUseCase
import com.practicum.playlistmaker.domain.usecase.UserTextWorkUseCase

class SearchUseCaseCreator(private val app: App) {
    fun getUserTextWork(): CreatorUserTextWorkModel{
        val userTextWorkImpl = UserTextWorkImpl()
        val userTextWorkUseCase = UserTextWorkUseCase(userTextWorkImpl)
        return CreatorUserTextWorkModel(userTextWorkImpl, userTextWorkUseCase)
    }

    fun getHistorySearchUseCase(): GetHistorySearchUseCase{
        val historySearch = HistorySearchImpl(app)
        val getHistorySearchUseCase = GetHistorySearchUseCase(historySearch)
        return getHistorySearchUseCase
    }
    fun getSearch(baseUrl: String): CreatorSearchModel{
        val runSearch = RunSearchImpl(baseUrl)
        val getSearchUseCase = GetSearchUseCase(runSearch)
        return CreatorSearchModel(runSearch, getSearchUseCase)
    }
    fun getClearHistory(): ClearHistoryUseCase{
        val clearHistory = ClearHistoryImpl(app)
        val clearHistoryUseCase = ClearHistoryUseCase(clearHistory)
        return clearHistoryUseCase
    }
}