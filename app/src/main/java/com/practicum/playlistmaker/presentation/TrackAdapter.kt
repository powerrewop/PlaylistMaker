package com.practicum.playlistmaker.presentation

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.domain.model.Track

class TrackAdapter(
    private var trackList: List<Track>
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_musiclist, parent, false)
        return TrackViewHolder(view)
    }
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {

            val intentInteractor = Creator.getIntentInteractor()
            val historySearchInteractor = Creator.getHistorySearchInteractor()

            historySearchInteractor.save(trackList[position])

            if (isClickAllowed) {
                isClickAllowed = false
                intentInteractor.openPlayer(trackList[position])
                handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
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