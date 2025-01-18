package com.practicum.playlistmaker.presentation.models

import com.practicum.playlistmaker.domain.model.Track



sealed class FavLibModel {
    class EmptyContent : FavLibModel()

    data class ShowFavLib(
        val trackList: List<Track>?
    ): FavLibModel()

    class LoadContent : FavLibModel()
}