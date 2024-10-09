package com.practicum.playlistmaker.data.storage

import com.google.gson.Gson
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.ParamDataRepository

class ParamData(private val  gson: Gson):
    ParamDataRepository {
    override fun get(paramJson: String): Track {
        val track = gson.fromJson(paramJson, Track::class.java)
        track.artworkUrl100 = track.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")
        return track

    }
}