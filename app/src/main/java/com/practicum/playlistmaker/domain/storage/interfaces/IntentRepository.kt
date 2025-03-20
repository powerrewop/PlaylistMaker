package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track

interface IntentRepository {
    fun openSend(textShare: String?)
    fun openSendTo()
    fun openView()

}