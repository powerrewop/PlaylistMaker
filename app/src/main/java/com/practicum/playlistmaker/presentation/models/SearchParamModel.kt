package com.practicum.playlistmaker.presentation.models

import com.practicum.playlistmaker.domain.model.Track

sealed class SearchParamModel {
    data class SearchContent(
        val tracks: List<Track>?,
        val userText: String
    ) : SearchParamModel()
    data class HistoryContent(
        val tracks: List<Track>?,
        val userText: String
    ) : SearchParamModel()
    data class ErrorContent(
        val tracks: List<Track>?,
        val userText: String
    ) : SearchParamModel()
    data class LoadingContent(
        val tracks: List<Track>?,
        val userText: String) : SearchParamModel()
    data class EmptyContent(
        val tracks: List<Track>?,
        val userText: String,
        val showRV: Boolean = true
    ) : SearchParamModel()
    data class NotFoundContent(
        val tracks: List<Track>?,
        val userText: String) : SearchParamModel()
}