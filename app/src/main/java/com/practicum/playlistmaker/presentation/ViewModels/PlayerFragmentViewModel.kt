package com.practicum.playlistmaker.presentation.ViewModels

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.model.PlayListTrack
import com.practicum.playlistmaker.domain.usecase.FavTrackInteractor
import com.practicum.playlistmaker.domain.usecase.IntentInteractor
import com.practicum.playlistmaker.domain.usecase.MediaplayerUseCase
import com.practicum.playlistmaker.domain.usecase.ParamDataUseCase
import com.practicum.playlistmaker.domain.usecase.TracksListsInteractor
import com.practicum.playlistmaker.presentation.UI.Converter.UiConverter
import com.practicum.playlistmaker.presentation.models.SelectPlayListModel
import com.practicum.playlistmaker.presentation.models.TrackParamModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class PlayerFragmentViewModel(
    private val paramDataUseCase: ParamDataUseCase,
    private val mediaplayerUseCase: MediaplayerUseCase,
    private val jsonTrack: String?,
    private val favTrackInteractor: FavTrackInteractor,
    private val intentInteractor: IntentInteractor,
    private val tracksListsInteractor: TracksListsInteractor
): ViewModel() {

    private lateinit var trackParamModel: TrackParamModel
    private val track = paramDataUseCase.getData(jsonTrack?:"")
    private var dataTrack = MutableLiveData(getTrackParamModel())

    private val selectPlayLists = MutableLiveData(SelectPlayListModel(false, null))

    fun getSelectPlayLists(): LiveData<SelectPlayListModel> {
        return selectPlayLists
    }

    private val setPlayImage = {
        trackParamModel.isPlay= false
        dataTrack.postValue(trackParamModel)
    }
    private val setPauseImage = {
        trackParamModel.isPlay= true
        dataTrack.postValue(trackParamModel)
    }
    private val setTimer = {
            textTimer: String ->
        trackParamModel.timerText = textTimer
        dataTrack.postValue(trackParamModel)
    }
    init{
        mediaplayerUseCase.preparePlayer(track.previewUrl, setPlayImage, setPauseImage, setTimer)
    }
    fun getDataTrack(): LiveData<TrackParamModel> {
        return dataTrack
    }
    fun buttonPlay(){
        mediaplayerUseCase.playbackControl()
    }
    fun pausePlayer(){
        mediaplayerUseCase.pausePlayer()
    }
    fun closePlayer(){
        mediaplayerUseCase.closePlayer()
    }
    private fun getYear(date: String?): String{
        if (date != null) {
            return date.substring(0, 4)
        }
        return ""
    }
    private fun getAlbumVis(collectionName: String?): Boolean{

        if (collectionName?.isEmpty() == true) {
            return false
        } else {
            return true
        }
    }
    private fun getTrackParamModel(): TrackParamModel {
        trackParamModel = TrackParamModel(
            track.artworkUrl100,
            track.trackName,
            track.collectionName,
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime),
            getYear(track.releaseDate),
            track.primaryGenreName,
            track.country,
            "00:00",
            getAlbumVis(track.collectionName),
            false,
            track.isFavorite
        )
        return trackParamModel
    }

    fun addToFav(){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                favTrackInteractor.saveFavTrack(track)
            }
        }

        intentInteractor.updateFav(track.trackId, true)

    }

    fun deleteFromFav(){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                favTrackInteractor.deleteFromFav(track)
            }
        }

        intentInteractor.updateFav(track.trackId, false)

    }

    fun buttonFavPressed(){
        if (track.isFavorite){
            deleteFromFav()
            track.isFavorite = false
            trackParamModel.isFav = false
            dataTrack.postValue(trackParamModel)
        }else{
            addToFav()
            track.isFavorite = true
            trackParamModel.isFav = true
            dataTrack.postValue(trackParamModel)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaplayerUseCase.closePlayer()
    }

    fun showSelectPlayLists(){

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
                            selectPlayLists.postValue(SelectPlayListModel(true, emptyList()))
                        } else {

                            it.forEach {
                                it.count = UiConverter.countTracksPlayList(it.id!!, allTracks)
                            }

                            selectPlayLists.postValue(SelectPlayListModel(true,  it))
                        }
                    } else {
                        selectPlayLists.postValue(SelectPlayListModel(true, emptyList()))
                    }
                }

        }

    }

    fun hideSelectPlayLists(){
        selectPlayLists.postValue(SelectPlayListModel(false, emptyList()))
    }

    fun addToPlayList(idList: Long, name: String, callBack: (isCreate: Boolean, name: String)->Unit){

        viewModelScope.launch {
            //Dispatchers.IO указан при создании flow

            tracksListsInteractor
                .getTrackThisList(idList, track.trackId)
                .collect {
                    if (it != null) {

                        if(it.isEmpty()){

                            withContext(Dispatchers.IO) {

                                tracksListsInteractor.addTrackToList(
                                    UiConverter.TrackToPlayListTrack(
                                        idList,
                                        track
                                    )
                                )
                            }

                            callBack.invoke(true, name)

                        }else{
                            callBack.invoke(false, name)
                        }

                    }
                    else{

                        withContext(Dispatchers.IO) {

                            tracksListsInteractor.addTrackToList(
                                UiConverter.TrackToPlayListTrack(
                                    idList,
                                    track
                                )
                            )
                        }

                        callBack.invoke(true, name)

                    }
                }
        }

    }
}