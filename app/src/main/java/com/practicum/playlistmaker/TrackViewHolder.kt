package com.practicum.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    private val trackName: TextView
    private val artistName: TextView
    private val albumImage: ImageView

    init {
        trackName = itemView.findViewById(R.id.sample_bigtext)
        artistName = itemView.findViewById(R.id.sample_smalltext)
        albumImage = itemView.findViewById(R.id.sample_iw)
    }

    fun bind(model: Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName + " * " + model.trackTime
        Glide.with(itemView).load(model.artworkUrl100).into(albumImage)
    }

}