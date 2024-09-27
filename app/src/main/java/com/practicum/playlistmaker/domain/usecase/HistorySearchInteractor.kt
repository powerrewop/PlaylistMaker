package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.HistorySearchRepository

class HistorySearchInteractor(private val historySearchRepository: HistorySearchRepository) {
    fun load(): List<Track>{
    return historySearchRepository.getHistory()
    }
    fun save(track: Track){
    historySearchRepository.saveHistory(track)
    }
    fun clear(){
        historySearchRepository.clearHistory()
    }
}