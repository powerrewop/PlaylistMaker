package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track


interface ParamDataRepository {
    fun get(paramJson: String): Track
}