package com.practicum.playlistmaker.presentation.ViewModels

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.PlayListTrack
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.IntentInteractor
import com.practicum.playlistmaker.domain.usecase.TracksListsInteractor
import com.practicum.playlistmaker.presentation.UI.Converter.UiConverter
import com.practicum.playlistmaker.presentation.models.ViewPlayListDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class ViewPlayListFragmentViewModel(
    private val tracksListsInteractor: TracksListsInteractor,
    private val jsonList: String?,
    private val gson: Gson,
    private val intentInteractor: IntentInteractor
): ViewModel() {

    private lateinit var playList: PlayList

    private var allTime: String = ""
    private var trackList: List<Track> = emptyList()

    private var isChoiseUser: Boolean = false

    var callbackEmptyPlayList: (()-> Unit)? = null

    private var viewPlayListDataModel: MutableLiveData<ViewPlayListDataModel> =
        MutableLiveData(ViewPlayListDataModel("", "", "", "", "", emptyList(), emptyList(), isChoiseUser))


    fun getTrackList(): PlayList{
        return playList
    }
    fun updateData() {

        playList = gson.fromJson(jsonList, PlayList::class.java)

        viewModelScope.launch {

            //Обновим данные плейлиста по его ИД с БД
            tracksListsInteractor
                .getPlayList(playList.id!!)
                .collect {
                    playList = it!!
                }
            /////////////////////////////////////////

            //Обновим кол-во треков в плейлисте
            tracksListsInteractor
                .getAllTracksThisList(playList.id!!)
                .collect {
                    if (it != null) {
                        playList.count = "треков: " + it.size
                    }else{
                        playList.count = "треков: 0"
                    }
                }
            /////////////////////////////////////////

            var tempList: MutableList<PlayList> = mutableListOf()

            tempList.add(playList)
            tempList.add(PlayList(null,"Поделиться",null,null,null,true))
            tempList.add(PlayList(null,"Редактировать информацию",null,null,null,true))
            tempList.add(PlayList(null,"Удалить плейлист",null,null,null,true))

            withContext(Dispatchers.IO) {
                val timeInt = tracksListsInteractor.getAllTimeTrack(playList.id!!)
                allTime = SimpleDateFormat("mm", Locale.getDefault()).format(timeInt)
                allTime+=" минут"
            }

            tracksListsInteractor
                .getAllTracksThisList(playList.id!!)
                .collect {
                    trackList = UiConverter.listListTracksToListTrack(it)

                }

            if (trackList.isEmpty() && callbackEmptyPlayList != null){
                callbackEmptyPlayList!!.invoke()
            }

            viewPlayListDataModel.postValue(
                ViewPlayListDataModel(
                    playList.image, playList.name, playList.desc, allTime, playList.count!!,
                    trackList, tempList.toList(), isChoiseUser
                )
            )
        }
    }

    fun setIsChoeseUser(ch: Boolean){

        isChoiseUser = ch

        viewPlayListDataModel.postValue(
            ViewPlayListDataModel(
                viewPlayListDataModel.value!!.image,
                viewPlayListDataModel.value!!.name,
                viewPlayListDataModel.value!!.desc,
                viewPlayListDataModel.value!!.allMinut,
                viewPlayListDataModel.value!!.count,
                viewPlayListDataModel.value!!.listTrack,
                viewPlayListDataModel.value!!.listPlayList,
                isChoiseUser))

    }

    fun delTrack(op: Track){

        var findPlayListTrack: PlayListTrack? = null

        viewModelScope.launch {

            tracksListsInteractor
                .getTrackThisList(playList.id!!, op.trackId)
                .collect {
                    if (it != null){
                        if (it.isNotEmpty()){
                            findPlayListTrack = it[0]
                        }
                    }
                }

            withContext(Dispatchers.IO) {

                if (findPlayListTrack != null){
                    tracksListsInteractor.deleteTrackFromList(findPlayListTrack!!)
                    updateData()
                }
            }
        }
    }

    fun delPlayList(callBackDeleteFinish: ()-> Unit){

        var removePlayListTracks: List<PlayListTrack>? = null

        viewModelScope.launch {
            tracksListsInteractor
                .getAllTracksThisList(playList.id!!)
                .collect {
                    removePlayListTracks = it
                }

            if (removePlayListTracks != null){

                withContext(Dispatchers.IO) {
                    removePlayListTracks!!.forEach {
                        tracksListsInteractor.deleteTrackFromList(it)
                    }
                }
            }

            withContext(Dispatchers.IO) {
                tracksListsInteractor.deleteList(playList)
            }
            callBackDeleteFinish.invoke()
        }
    }

    fun getViewPlayListDataModel(): LiveData<ViewPlayListDataModel> {
        return viewPlayListDataModel
    }

    fun sharePlayList(){

        var inc: Int = 1
        var textIntent = ""
        textIntent += playList.name + "\n"
        textIntent += playList.desc + "\n"
        textIntent += playList.count + "\n"

        trackList.forEach {
            textIntent += inc.toString() + ". " + it.artistName + " - " + it.trackName + " (" + SimpleDateFormat("mm:ss", Locale.getDefault()).format(it.trackTime) + ")" + "\n"
            inc++
        }
        intentInteractor.openSend(textIntent)
    }

    fun listsTrackIsEmpty(): Boolean{
        return trackList.isEmpty()
    }

}