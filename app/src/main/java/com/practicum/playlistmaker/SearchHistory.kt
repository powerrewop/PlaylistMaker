package com.practicum.playlistmaker

import com.google.gson.Gson

fun getHistorySearch(myApp: App): MutableList<Track>{

    val arrayTrack = getDataSharedPrefs(myApp)
    return arrayTrack.asList().toMutableList()

}

fun saveHistorySearch(myApp: App, newElement: Track){

    val oldArrayTrack = getDataSharedPrefs(myApp)
    val oldListTrack: MutableList<Track> = oldArrayTrack.toMutableList()

    for(it in oldListTrack){
        if (newElement.trackId == it.trackId){
            oldListTrack.remove(it)
            break
        }
    }

    oldListTrack.add(0,newElement)

    var i = 1
    oldListTrack.forEach{
    if (i > 10){
        oldListTrack.remove(it)
    }
    i++
}
    val newArrayTrack = oldListTrack.toTypedArray()

    val json = Gson().toJson(newArrayTrack)
    myApp.sharedPrefs?.edit()
        ?.putString(HISTORY_SEARCH, json)
        ?.apply()

}

fun getDataSharedPrefs(myApp: App): Array<Track>{

    val json = myApp.sharedPrefs?.getString(HISTORY_SEARCH, null) ?: return emptyArray<Track>()
    var arrayTrack =  Gson().fromJson(json, Array<Track>::class.java)

    arrayTrack.forEach {
        it.isHistory = true
    }

    return arrayTrack

}

fun clearHistory(myApp: App){

    val emptyArray = emptyArray<Track>()
    val json = Gson().toJson(emptyArray)
    myApp.sharedPrefs?.edit()
        ?.putString(HISTORY_SEARCH, json)
        ?.apply()

}