package com.practicum.playlistmaker.presentation

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.SampleMusiclistBinding
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.HistorySearchInteractor
import com.practicum.playlistmaker.domain.usecase.IntentInteractor
import org.koin.java.KoinJavaComponent.getKoin

class TrackAdapter(
    private var trackList: List<Track>
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        val layoutInspector = LayoutInflater.from(parent.context)
        return TrackViewHolder(SampleMusiclistBinding.inflate(layoutInspector, parent, false))
    }
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {

            val intentInteractor: IntentInteractor = getKoin().get()
            val historySearchInteractor: HistorySearchInteractor = getKoin().get()

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