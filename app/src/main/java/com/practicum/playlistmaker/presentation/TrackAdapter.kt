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
                intentInteractor.openPlayer(trackList[position])
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
    override fun getItemCount() = trackList.size
    fun updateTrack(newTrackList: List<Track>) {
        trackList = newTrackList
    }
    companion object{
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}