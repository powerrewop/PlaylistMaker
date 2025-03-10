package com.practicum.playlistmaker.presentation.models

import com.practicum.playlistmaker.domain.model.PlayList

sealed class PlayListMediaFragmentModel {

    class loadContent : PlayListMediaFragmentModel()
    class emptyContent : PlayListMediaFragmentModel()

    data class showPlayLists(
        val listPlaylists: List<PlayList>
    ) : PlayListMediaFragmentModel()
}

