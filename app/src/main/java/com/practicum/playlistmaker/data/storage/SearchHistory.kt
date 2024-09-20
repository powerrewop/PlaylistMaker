package com.practicum.playlistmaker.data.storage

import com.google.gson.Gson
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.HISTORY_SEARCH
import com.practicum.playlistmaker.domain.model.Track

fun getHistorySearch(myApp: App): List<Track> {

    val arrayTrack = getDataSharedPrefs(myApp)
    //return arrayTrack.asList().toMutableList()
    return arrayTrack.toList()

}

fun saveHistorySearch(myApp: App, newElement: Track) {

    val oldArrayTrack = getDataSharedPrefs(myApp)
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
    myApp.sharedPrefs?.edit()
        ?.putString(HISTORY_SEARCH, json)
        ?.apply()

}

fun getDataSharedPrefs(myApp: App): Array<Track> {

    val json = myApp.sharedPrefs?.getString(HISTORY_SEARCH, null) ?: return emptyArray<Track>()
    var arrayTrack = Gson().fromJson(json, Array<Track>::class.java)

    arrayTrack.forEach {
        it.isHistory = true
    }

    return arrayTrack

}

fun clearHistory(myApp: App) {

    val emptyArray = emptyArray<Track>()
    val json = Gson().toJson(emptyArray)
    myApp.sharedPrefs?.edit()
        ?.putString(HISTORY_SEARCH, json)
        ?.apply()

}