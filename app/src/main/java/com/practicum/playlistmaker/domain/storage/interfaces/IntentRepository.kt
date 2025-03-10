package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track

interface IntentRepository {
    fun openSend()
    fun openSendTo()
    fun openView()

}