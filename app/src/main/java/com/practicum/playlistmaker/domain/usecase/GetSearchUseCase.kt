package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.RunSearch

class GetSearchUseCase(private val runSearch: RunSearch) {
    fun search(textSearch: String){
        runSearch.search(textSearch)
    }
}