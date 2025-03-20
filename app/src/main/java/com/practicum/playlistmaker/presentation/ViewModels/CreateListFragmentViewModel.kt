package com.practicum.playlistmaker.presentation.ViewModels

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.usecase.TracksListsInteractor
import com.practicum.playlistmaker.presentation.models.CreateListModel
import com.practicum.playlistmaker.presentation.models.EditListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateListFragmentViewModel(
    private val tracksListsInteractor: TracksListsInteractor,
    private val gson: Gson
): ViewModel() {

    private var valImage: Uri? = null
    private var editMode = false
    private var playList: PlayList? = null

    private var createListModel: MutableLiveData<CreateListModel> =
        MutableLiveData(CreateListModel.statCreateList(valImage, false))

    private var editListModel: MutableLiveData<EditListModel> =
        MutableLiveData(EditListModel("",null,null))

    private var editModeButtonSaveActive: MutableLiveData<Boolean> =
        MutableLiveData(true)


    fun isEditMode(): Boolean{
        return editMode
    }

    fun enableEditMode(plGson: String?){
        playList = gson.fromJson(plGson, PlayList::class.java)
        editMode = true
        valImage = playList!!.image?.toUri()
        editListModel.postValue(EditListModel(playList!!.name, valImage, playList!!.desc))
    }

    fun getCreateListModel(): LiveData<CreateListModel> {
        return createListModel
    }

    fun getEditListModel(): LiveData<EditListModel> {
        return editListModel
    }

    fun getEditModeButtonSaveActive(): LiveData<Boolean> {
        return editModeButtonSaveActive
    }

    fun userChangeText(valName: String, valDesc: String){

        if(!editMode) {

            if (valName.isNotEmpty()) {
                createListModel.postValue(CreateListModel.statCreateList(valImage, true))
            } else {
                createListModel.postValue(CreateListModel.statCreateList(valImage, false))
            }

        }else{
            userChangeTextEditModev2(valName)
        }

    }

    fun userChangeTextEditModev2(valName: String){

        if (valName.isNotEmpty()) {
           editModeButtonSaveActive.postValue(true)
        } else {
            editModeButtonSaveActive.postValue(false)
        }
    }

    fun userChangeTextEditMode(valName: String, valDesc: String){

        if (valName.isNotEmpty()){
            editListModel.postValue(EditListModel(valName, valImage, valDesc))
        }else{
            editListModel.postValue(EditListModel(valName, valImage, valDesc))
        }

    }

    fun createPlayList(valName: String, valDesc: String){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                tracksListsInteractor.createList(PlayList(null, valName, valImage.toString(), valDesc,""))
            }
        }

    }

    fun loadAlbumImage(uri: Uri, valName: String, valDesc: String){

        valImage = uri

        if (!editMode) {
            userChangeText(valName, valDesc)
        }else{
            userChangeTextEditMode(valName, valDesc)
        }
    }

    fun isImageLoad(): Boolean {
        if (valImage != null) {
            return true
        }
        return false
    }

    fun editPlayList(valName: String, valDesc: String){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                tracksListsInteractor.createList(PlayList(playList?.id, valName, valImage.toString(), valDesc, null, false))
            }
        }

    }


}