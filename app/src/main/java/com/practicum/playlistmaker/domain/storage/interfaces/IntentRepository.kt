package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track

interface IntentRepository {
    fun openSend()
    fun openSendTo()
    fun openView()
    fun openSearch()
    fun openMedia()
    fun openSettings()
    fun openPlayer(track: Track)
}