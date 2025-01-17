package com.practicum.playlistmaker.presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.FavTrackInteractor
import com.practicum.playlistmaker.domain.usecase.HistorySearchInteractor
import com.practicum.playlistmaker.domain.usecase.LoadTracksUseCase
import com.practicum.playlistmaker.presentation.models.SearchParamModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val USER_DELAY = 2000L
class SearchFragmentViewModel(
    private val historySearchInteractor: HistorySearchInteractor,
    private val loadTracksUseCase: LoadTracksUseCase,
    private val favTrackInteractor: FavTrackInteractor
) : ViewModel()
{
    private var saveTracks: List<Track>? = emptyList()
    private var userText: String = ""

    private  var searchJob: Job? = null

    private var searchParamModel: MutableLiveData<SearchParamModel> =
        MutableLiveData(SearchParamModel.EmptyContent(saveTracks, userText))

    fun getSearchParamModel(): LiveData<SearchParamModel> {
        return searchParamModel
    }

    fun setFocus(hasFocus: Boolean, userText: String) {

        this.userText = userText

        if (hasFocus && userText.isEmpty()) {
            workHistory()
        } else {
            searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText))
        }
    }

    fun textChange(hasFocus: Boolean, s: CharSequence?) {

        userText = s.toString()

        if (hasFocus && s?.isEmpty() == true) {

            workHistory()

        } else if (hasFocus && s?.isEmpty() == false) {

            searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText, false))

            /////////////////////////////////
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(USER_DELAY)
                runSearch(s)
            }
            ////////////////////////////////

        } else {
            searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText))
        }
    }

    private suspend fun runSearch(s: CharSequence?) {

        searchParamModel.postValue(SearchParamModel.LoadingContent(saveTracks, userText))

        viewModelScope.launch {

            //Dispatchers.IO указан при создании flow
            loadTracksUseCase
                .load(s.toString())
                .collect {

                    if (it.isError) {
                        postSearch(400, emptyList())
                    } else {
                        postSearch(200, it.results)
                    }
                }
        }
    }

    fun clear() {
        workHistory()
    }
    fun clearHsitory() {
        historySearchInteractor.clear()
        searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText, false))
    }

    fun postSearch(resultCode: Int, tracks: List<Track>?) {

        if ((resultCode == 200) && ((tracks == null) || (tracks.isEmpty()))) {
            searchParamModel.postValue(SearchParamModel.NotFoundContent(emptyList(), userText))
        } else if (resultCode != 200) {
            searchParamModel.postValue(SearchParamModel.ErrorContent(emptyList(), userText))
        } else {
            searchParamModel.postValue(SearchParamModel.SearchContent(tracks, userText))
            saveTracks = tracks
        }
    }
    private fun workHistory(){

        var favTrackId:List<Long> = emptyList()

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                saveTracks = historySearchInteractor.load()

                favTrackInteractor
                    .getAllIdFavTracks()
                    .collect {
                        favTrackId = it
                    }
            }

            saveTracks?.forEach {
                it.isFavorite = isFavTrack(it.trackId, favTrackId)
            }

            val historyContent = SearchParamModel.HistoryContent(saveTracks, userText)
            if (saveTracks!!.isNotEmpty()) {
                searchParamModel.postValue(historyContent)
            } else {
                searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText))
            }
        }

    }

    private fun isFavTrack(idTrack: Long, favIdList: List<Long>): Boolean{

        favIdList.forEach {
            if (it == idTrack){
                return true
            }
        }

        return false

    }
}