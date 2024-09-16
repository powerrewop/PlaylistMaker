package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.IntentUser


class GetIntentsUsaCase(private val intentUser: IntentUser) {

    fun getUserIntent(): IntentUser {
        return intentUser
    }

}