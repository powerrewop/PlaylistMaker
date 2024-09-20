package com.practicum.playlistmaker.presentation

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.data.storage.HistorySearchImpl
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.GetHistorySearchUseCase

class TrackAdapter(
    private var trackList: List<Track>
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    lateinit var onOpenElement: (Track, Context) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_musiclist, parent, false)
        return TrackViewHolder(view)
    }
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            val historySearch = HistorySearchImpl(holder.itemView.context.applicationContext as App)
            val historyUseCase = GetHistorySearchUseCase(historySearch)
            historyUseCase.saveHistorySearch(trackList[position])

            ////Открытие формы плейера, реализация в OptionsSearchActivity
            if (isClickAllowed) {
                isClickAllowed = false
                onOpenElement.invoke(trackList[position], holder.itemView.context)
                handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
            }
            ////////////////////////////

            if (trackList[position].isHistory) {
                trackList = historyUseCase.getHistorySearch()
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