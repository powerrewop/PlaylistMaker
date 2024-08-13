package com.practicum.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

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

            saveHistorySearch(
                (holder.itemView.context.applicationContext as App),
                trackList[position]
            )

            if (isClickAllowed) {

                isClickAllowed = false
                val json = Gson().toJson(trackList[position])
                val displayIntent = Intent(holder.itemView.context, PlayerActivity::class.java)
                displayIntent.putExtra("TrackData", json)
                holder.itemView.context.startActivity(displayIntent)

                handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)

            }

            if (trackList[position].isHistory) {
                trackList = getHistorySearch(holder.itemView.context.applicationContext as App)
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