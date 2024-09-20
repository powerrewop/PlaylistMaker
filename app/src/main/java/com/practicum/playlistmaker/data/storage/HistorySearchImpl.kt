package com.practicum.playlistmaker.data.storage

import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.HistorySearch

class HistorySearchImpl(val myApp: App): HistorySearch {
    override fun get(): List<Track> {
        return getHistorySearch(myApp)
    }
    override fun save(track: Track) {
        saveHistorySearch(myApp, track)
    }
}