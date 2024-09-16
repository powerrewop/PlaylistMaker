package com.practicum.playlistmaker.creator

import android.app.Activity
import com.practicum.playlistmaker.data.storage.ParamDataImpl
import com.practicum.playlistmaker.data.storage.PlayerControlImpl
import com.practicum.playlistmaker.domain.usecase.GetTrackDataByParamUseCase
import com.practicum.playlistmaker.domain.usecase.PlayerControlUseCase
import com.practicum.playlistmaker.presentation.OptionsPlayerActivity

class PlayerUseCaseCreator(private val act: Activity) {
    fun getPlayerControlUseCaseAndPlayerControl(): CreatorPlayerControlModel {
        val playerControl = PlayerControlImpl()
        val playerControlUseCase = PlayerControlUseCase(playerControl)
        return CreatorPlayerControlModel(playerControl, playerControlUseCase)
    }
    fun getTrackDataByParamUseCase(): GetTrackDataByParamUseCase {
        val paramData = ParamDataImpl(act)
        return GetTrackDataByParamUseCase(paramData)
    }
    fun getOptionsPlayerActivity(): OptionsPlayerActivity {
        return OptionsPlayerActivity()
    }
}