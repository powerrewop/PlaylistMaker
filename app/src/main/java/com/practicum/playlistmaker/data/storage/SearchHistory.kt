package com.practicum.playlistmaker.data.storage

import com.google.gson.Gson
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.HISTORY_SEARCH
import com.practicum.playlistmaker.domain.model.Track
import org.koin.java.KoinJavaComponent.getKoin

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

        val gson: Gson = getKoin().get()
        val gsonString = gson.toJson(newArrayTrack)
        App.sharedPrefs.edit()
            ?.putString(HISTORY_SEARCH, gsonString)
            ?.apply()
    }
    private fun getDataSharedPrefs(): Array<Track> {

        val gson: Gson = getKoin().get()
        val gsonString = App.sharedPrefs.getString(HISTORY_SEARCH, null) ?: return emptyArray<Track>()
        var arrayTrack = gson.fromJson(gsonString, Array<Track>::class.java)

        arrayTrack.forEach {
            it.isHistory = true
        }

        return arrayTrack
    }
    fun clearHistory() {
        val gson: Gson = getKoin().get()
        val emptyArray = emptyArray<Track>()
        val gsonString = gson.toJson(emptyArray)
        App.sharedPrefs.edit()
            ?.putString(HISTORY_SEARCH, gsonString)
            ?.apply()
    }
}