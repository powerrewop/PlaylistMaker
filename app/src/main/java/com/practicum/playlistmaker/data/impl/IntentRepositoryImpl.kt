package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.storage.IntentWork
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.IntentRepository

class IntentRepositoryImpl(private val intentWork: IntentWork): IntentRepository {
    override fun openSend() {
        intentWork.openSend()
    }

    override fun openSendTo() {
        intentWork.openSendTo()
    }

    override fun openView() {
        intentWork.openView()
    }

}