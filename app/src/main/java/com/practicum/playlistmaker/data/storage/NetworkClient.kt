package com.practicum.playlistmaker.data.storage

import com.practicum.playlistmaker.data.model.ItunesDataModel

class NetworkClient(private val iTunesService: ItunesApiService) {
    suspend fun getSearchResult(textSearch: String): ItunesDataModel{

        var res: ItunesDataModel

        try {
            res = iTunesService.search(textSearch)
            res.apply {
                isError = false
                textError = ""
            }
        }catch(e: Throwable) {
            res = ItunesDataModel(null, true, e.toString())
        }
        return res
    }
}