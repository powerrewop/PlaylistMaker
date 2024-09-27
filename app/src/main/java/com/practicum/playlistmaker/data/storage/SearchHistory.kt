package com.practicum.playlistmaker.data.storage

import com.google.gson.Gson
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.HISTORY_SEARCH
import com.practicum.playlistmaker.domain.model.Track

class SearchHistory() {
    fun getHistorySearch(): List<Track> {
        val arrayTrack = getDataSharedPrefs()
        return arrayTrack.toList()
    }

    fun saveHistorySearch(newElement: Track) {

        val oldArrayTrack = getDataSharedPrefs()
        val oldListTrack: MutableList<Track> = oldArrayTrack.toMutableList()

        var iterator = oldListTrack.iterator()
        while (iterator.hasNext()) {
            val track = iterator.next()
            if (newElement.trackId == track.trackId) {
                iterator.remove()
            }
        }

        oldListTrack.add(0, newElement)

        var i = 1
        var iterator2 = oldListTrack.iterator()
        while (iterator2.hasNext()) {
            iterator2.next()
            if (i > 10) {
                iterator2.remove()
            }
            i++
        }

        val newArrayTrack = oldListTrack.toTypedArray()

        val json = Gson().toJson(newArrayTrack)
        App.sharedPrefs.edit()
            ?.putString(HISTORY_SEARCH, json)
            ?.apply()
    }
    private fun getDataSharedPrefs(): Array<Track> {

        val json = App.sharedPrefs.getString(HISTORY_SEARCH, null) ?: return emptyArray<Track>()
        var arrayTrack = Gson().fromJson(json, Array<Track>::class.java)

        arrayTrack.forEach {
            it.isHistory = true
        }

        return arrayTrack
    }
    fun clearHistory() {
        val emptyArray = emptyArray<Track>()
        val json = Gson().toJson(emptyArray)
        App.sharedPrefs.edit()
            ?.putString(HISTORY_SEARCH, json)
            ?.apply()
    }
}