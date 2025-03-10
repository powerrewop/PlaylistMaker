package com.practicum.playlistmaker.presentation.models

import com.practicum.playlistmaker.domain.model.PlayList

data class SelectPlayListModel(
    val showBottomSheet: Boolean,
    val listLists: List<PlayList>?
) {
}