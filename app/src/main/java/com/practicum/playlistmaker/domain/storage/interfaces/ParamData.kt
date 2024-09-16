package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track


interface ParamData {
    fun get(paramName: String): Track
}