package com.practicum.playlistmaker.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.SampleMusiclistBinding
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.HistorySearchInteractor
import com.practicum.playlistmaker.domain.usecase.IntentInteractor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrackAdapter(
    private var trackList: List<Track>,
    private val intentInteractor: IntentInteractor,
    private val historySearchInteractor: HistorySearchInteractor
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var isClickAllowed = true
    var isFavForm = false
    var callBackOpenPlayer: ((track: Track)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        val layoutInspector = LayoutInflater.from(parent.context)
        return TrackViewHolder(SampleMusiclistBinding.inflate(layoutInspector, parent, false))
    }
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {

            historySearchInteractor.save(trackList[position])

            if (isClickAllowed) {
                isClickAllowed = false

                intentInteractor.callBack = ::updateFav

                if  (callBackOpenPlayer != null) {
                    callBackOpenPlayer!!.invoke(trackList[position])
                }

                GlobalScope.launch {
                    delay(CLICK_DEBOUNCE_DELAY)
                    isClickAllowed = true
                }

            }
            if (trackList[position].isHistory) {
                trackList = historySearchInteractor.load()
                notifyDataSetChanged()
            }
        }
    }

    fun updateFav(idTrack: Long, newFav: Boolean){

        if (isFavForm){

            var tempList: MutableList<Track>? = mutableListOf()

            trackList.forEach {

                if(it.trackId != idTrack){
                    tempList?.add(it)
                }

            }

            if (tempList != null) {
                trackList = tempList.toList()
                notifyDataSetChanged()
            }

        }else{

            trackList.forEach {
                if(it.trackId == idTrack){
                    it.isFavorite = newFav
                }
            }

        }
    }
    override fun getItemCount() = trackList.size
    fun updateTrack(newTrackList: List<Track>) {
        trackList = newTrackList
    }
    companion object{
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}