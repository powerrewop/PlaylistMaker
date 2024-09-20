package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.HistorySearch

class GetHistorySearchUseCase(private val historySearch: HistorySearch) {
    fun getHistorySearch(): List<Track>{
        return historySearch.get()
    }

    fun saveHistorySearch(track: Track){
        historySearch.save(track)
    }
}