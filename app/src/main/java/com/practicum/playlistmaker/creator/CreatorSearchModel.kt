package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.storage.RunSearchImpl
import com.practicum.playlistmaker.domain.usecase.GetSearchUseCase

class CreatorSearchModel(
    val runSearch: RunSearchImpl,
    val getSearchUseCase: GetSearchUseCase
)