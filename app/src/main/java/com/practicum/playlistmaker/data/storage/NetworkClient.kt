package com.practicum.playlistmaker.data.storage

import com.practicum.playlistmaker.data.model.ItunesDataModel
import retrofit2.Callback

class NetworkClient(private val iTunesService: ItunesApiService) {

    fun getSearchResult(textSearch: String, callback: (Callback<ItunesDataModel>)){
        iTunesService.search(textSearch).enqueue(callback)
    }
}