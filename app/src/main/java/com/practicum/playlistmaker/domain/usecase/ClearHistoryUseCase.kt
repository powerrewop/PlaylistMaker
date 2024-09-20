package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.ClearHistory

class ClearHistoryUseCase(private val clearHistory: ClearHistory) {
    fun clearHistory(){
        clearHistory.clearHistory()
    }
}