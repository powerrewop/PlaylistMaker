package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.ParamDataRepository

class ParamDataUseCase(private val paramData: ParamDataRepository) {
    fun getData(paramJson: String): Track{
        return paramData.get(paramJson)
    }
}