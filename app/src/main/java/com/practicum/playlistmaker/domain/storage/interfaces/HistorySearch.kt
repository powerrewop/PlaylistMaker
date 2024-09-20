package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track

interface HistorySearch {
    fun get(): List<Track>
    fun save(track: Track)
}