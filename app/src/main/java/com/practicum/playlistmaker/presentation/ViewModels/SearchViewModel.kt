package com.practicum.playlistmaker.presentation.ViewModels

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.HistorySearchInteractor
import com.practicum.playlistmaker.domain.usecase.LoadTracksUseCase
import com.practicum.playlistmaker.presentation.models.SearchParamModel

const val SEARCH_REQUEST_TOKEN = "SEARCH_REQUEST_TOKEN"
const val USER_DELAY = 2000L
class SearchViewModel(
    private val historySearchInteractor: HistorySearchInteractor,
    private val loadTracksUseCase: LoadTracksUseCase
) : ViewModel() {

    private var saveTracks: List<Track>? = emptyList()
    private var userText: String = ""
    val handler = Handler(Looper.getMainLooper())

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
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        if (hasFocus && s?.isEmpty() == true) {

            workHistory()

        } else if (hasFocus && s?.isEmpty() == false) {

            searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText, false))
            //////////////////////////////////////////////
            val runSearch = Runnable {
                searchParamModel.postValue(SearchParamModel.LoadingContent(saveTracks, userText))
                loadTracksUseCase.load(s.toString(), { tracks ->
                    postSearch(200, tracks)
                }, {
                    postSearch(400, emptyList())
                })
            }
            handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
            handler.postAtTime(
                runSearch,
                SEARCH_REQUEST_TOKEN,
                SystemClock.uptimeMillis() + USER_DELAY,
            )
            //////////////////////////////////////////////

        } else {
            searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText))
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
        saveTracks = historySearchInteractor.load()
        val historyContent = SearchParamModel.HistoryContent(saveTracks, userText)
        if (saveTracks!!.isNotEmpty()) {
            searchParamModel.postValue(historyContent)
        } else {
            searchParamModel.postValue(SearchParamModel.EmptyContent(saveTracks, userText))
        }
    }
}

