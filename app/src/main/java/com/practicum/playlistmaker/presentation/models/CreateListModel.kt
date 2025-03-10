package com.practicum.playlistmaker.presentation.models

import android.net.Uri

sealed class CreateListModel {

    data class statCreateList(
        val image: Uri?,
        val buttonCreateActive: Boolean
    ): CreateListModel()

}