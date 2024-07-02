package com.practicum.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(
    private var trackList: List<Track>
) : RecyclerView.Adapter<TrackViewHolder> ()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_musiclist, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])

        holder.itemView.setOnClickListener{

                saveHistorySearch(
                    (holder.itemView.context.applicationContext as App),
                    trackList[position]
                )

        }
    }

    override fun getItemCount() = trackList.size

    fun updateTrack(newTrackList: List<Track>){
        trackList = newTrackList
    }


}