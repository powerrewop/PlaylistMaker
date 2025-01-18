package com.practicum.playlistmaker.presentation.ViewModels.Media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.FavTrackInteractor
import com.practicum.playlistmaker.presentation.models.FavLibModel
import kotlinx.coroutines.launch

class FavoritesMediaFragmentViewModel(private val favTrackInteractor: FavTrackInteractor): ViewModel() {

    private var favParamModel: MutableLiveData<FavLibModel> =
        MutableLiveData(FavLibModel.LoadContent())

    private var favTracks: List<Track>? = emptyList()

    fun getFavTracks(){

        favParamModel.postValue(FavLibModel.LoadContent())

        viewModelScope.launch {
            favTrackInteractor.
            getFavTracks().
            collect {
                if (!it.isNullOrEmpty()) {
                    favParamModel.postValue(FavLibModel.ShowFavLib(it))
                }else{
                    favParamModel.postValue(FavLibModel.EmptyContent())
                }
            }
        }
    }

        fun getFavParamModel(): LiveData<FavLibModel> {
            return favParamModel
        }



}