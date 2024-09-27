package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track

interface HistorySearchRepository {
    fun getHistory(): List<Track>
    fun saveHistory(track: Track)
    fun clearHistory()
}