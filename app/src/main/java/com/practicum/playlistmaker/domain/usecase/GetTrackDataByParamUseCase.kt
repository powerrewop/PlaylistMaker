package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.ParamData

class GetTrackDataByParamUseCase(private val paramData: ParamData) {
    fun getData(paramName: String): Track{
        return paramData.get(paramName)
    }
}