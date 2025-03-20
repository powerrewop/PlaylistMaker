package com.practicum.playlistmaker.presentation.models

import android.net.Uri

data class EditListModel(
    var name: String,
    var image: Uri?,
    var desc: String?
)