package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.storage.UserTextWorkImpl
import com.practicum.playlistmaker.domain.usecase.UserTextWorkUseCase

class CreatorUserTextWorkModel(
    val userTextWorkImpl: UserTextWorkImpl,
    val userTextWorkUseCase: UserTextWorkUseCase
)