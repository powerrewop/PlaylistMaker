package com.practicum.playlistmaker.presentation.models

import com.practicum.playlistmaker.data.db.entity.TracksLists
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.Track

data class ViewPlayListDataModel(
    val image: String?,
    val name: String,
    val desc: String?,
    val allMinut: String,
    val count: String,
    val listTrack: List<Track>?,
    val listPlayList: List<PlayList>?,
    val isUserChoise: Boolean
)