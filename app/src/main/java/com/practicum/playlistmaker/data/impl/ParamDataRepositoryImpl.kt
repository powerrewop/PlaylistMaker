package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.storage.ParamData
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.ParamDataRepository

class ParamDataRepositoryImpl(private val paramData: ParamData): ParamDataRepository {
    override fun get(paramJson: String): Track {
        return paramData.get(paramJson)
    }
}