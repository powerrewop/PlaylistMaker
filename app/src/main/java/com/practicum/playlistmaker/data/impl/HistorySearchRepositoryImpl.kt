package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.storage.SearchHistory
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.HistorySearchRepository

class HistorySearchRepositoryImpl(private val searchHistory: SearchHistory): HistorySearchRepository{
    override fun getHistory(): List<Track> {
        return searchHistory.getHistorySearch()
    }

    override fun saveHistory(track: Track) {
        searchHistory.saveHistorySearch(track)
    }

    override fun clearHistory() {
        searchHistory.clearHistory()
    }

}