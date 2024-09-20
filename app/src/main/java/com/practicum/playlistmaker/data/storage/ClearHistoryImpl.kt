package com.practicum.playlistmaker.data.storage

import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.domain.storage.interfaces.ClearHistory

class ClearHistoryImpl(private val app: App):ClearHistory {
    override fun clearHistory() {
        clearHistory((app))
    }
}