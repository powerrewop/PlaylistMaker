package com.practicum.playlistmaker.presentation.ViewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.usecase.TracksListsInteractor
import com.practicum.playlistmaker.presentation.models.CreateListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateListFragmentViewModel(private val tracksListsInteractor: TracksListsInteractor): ViewModel() {

    private var valImage: Uri? = null

    private var createListModel: MutableLiveData<CreateListModel> =
        MutableLiveData(CreateListModel.statCreateList(valImage, false))

    fun getCreateListModel(): LiveData<CreateListModel> {
        return createListModel
    }

    fun userChangeText(valName: String){

        if (valName.isNotEmpty()){
            createListModel.postValue(CreateListModel.statCreateList(valImage, true))
        }else{
            createListModel.postValue(CreateListModel.statCreateList(valImage, false))
        }

    }

    fun createPlayList(valName: String, valDesc: String){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                tracksListsInteractor.createList(PlayList(null, valName, valImage.toString(), valDesc,""))
            }
        }

    }

    fun loadAlbumImage(uri: Uri, valName: String){
        valImage = uri
        userChangeText(valName)
    }

    fun isImageLoad(): Boolean {
        if (valImage != null) {
            return true
        }
        return false
    }


}