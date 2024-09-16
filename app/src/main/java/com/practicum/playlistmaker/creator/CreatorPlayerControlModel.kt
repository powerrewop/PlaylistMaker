package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.storage.PlayerControlImpl
import com.practicum.playlistmaker.domain.usecase.PlayerControlUseCase

class CreatorPlayerControlModel(
    val playerControl: PlayerControlImpl,
    val playerControlUseCase: PlayerControlUseCase
)