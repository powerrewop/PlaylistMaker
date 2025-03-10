package com.practicum.playlistmaker.presentation.ViewModels.Media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.model.PlayListTrack
import com.practicum.playlistmaker.domain.usecase.TracksListsInteractor
import com.practicum.playlistmaker.presentation.UI.Converter.UiConverter
import com.practicum.playlistmaker.presentation.models.PlayListMediaFragmentModel
import kotlinx.coroutines.launch

class PlayListsMediaFragmentViewModel(private val tracksListsInteractor: TracksListsInteractor): ViewModel() {

    private var listsModel: MutableLiveData<PlayListMediaFragmentModel> =
        MutableLiveData(PlayListMediaFragmentModel.loadContent())

    fun getListsModel(): LiveData<PlayListMediaFragmentModel> {
        return listsModel
    }

    fun readPlayLists(){

        listsModel.postValue(PlayListMediaFragmentModel.loadContent())

        var allTracks: List<PlayListTrack> = emptyList()

        viewModelScope.launch {
            //Dispatchers.IO указан при создании flow

            tracksListsInteractor
                .getAllTracks()
                .collect{
                    if (it != null) {
                        allTracks = it
                    }
                }

            tracksListsInteractor
                .getAllLists()
                .collect {

                    if (it != null) {
                        if (it.isEmpty()) {
                            listsModel.postValue(PlayListMediaFragmentModel.emptyContent())
                        } else {

                            it.forEach {
                                it.count = UiConverter.countTracksPlayList(it.id!!, allTracks)
                            }

                            listsModel.postValue(PlayListMediaFragmentModel.showPlayLists(it))
                        }
                    } else {
                        listsModel.postValue(PlayListMediaFragmentModel.emptyContent())
                    }
                }
        }
    }

}