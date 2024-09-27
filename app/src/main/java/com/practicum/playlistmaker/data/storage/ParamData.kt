package com.practicum.playlistmaker.data.storage

import com.google.gson.Gson
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.ParamDataRepository

class ParamData():
    ParamDataRepository {
    override fun get(paramJson: String): Track {
        val track = Gson().fromJson(paramJson, Track::class.java)
        track.artworkUrl100 = track.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")
        return track

    }
}