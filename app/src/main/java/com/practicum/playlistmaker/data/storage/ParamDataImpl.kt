package com.practicum.playlistmaker.data.storage

import android.app.Activity
import com.google.gson.Gson
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.ParamData

class ParamDataImpl(private val act: Activity): ParamData {
    override fun get(paramName: String): Track {

        val arguments = act.getIntent().getExtras()
        val json = arguments?.getString("TrackData")
        val track = Gson().fromJson(json, Track::class.java)
        track.artworkUrl100 = track.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")
        return track

    }
}