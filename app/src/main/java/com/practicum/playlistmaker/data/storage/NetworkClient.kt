package com.practicum.playlistmaker.data.storage

import com.practicum.playlistmaker.data.model.ItunesDataModel
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient(private val baseUrlIyunes: String) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrlIyunes)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ItunesApiService::class.java)
    fun getSearchResult(textSearch: String, callback: (Callback<ItunesDataModel>)){
        iTunesService.search(textSearch).enqueue(callback)
    }
}