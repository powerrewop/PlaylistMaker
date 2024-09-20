package com.practicum.playlistmaker.presentation

import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import java.util.Locale

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView
    private val artistName: TextView
    private val trackTime: TextView
    private val albumImage: ImageView

    init {
        trackName = itemView.findViewById(R.id.sample_bigtext)
        artistName = itemView.findViewById(R.id.sample_smalltext)
        trackTime = itemView.findViewById(R.id.sample_tracklen)
        albumImage = itemView.findViewById(R.id.sample_iw)
    }

    fun bind(model: Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTime)

        Glide.with(itemView).load(model.artworkUrl100).placeholder(R.drawable.empty_image)
            .centerCrop().transform(RoundedCorners(2)).into(albumImage)
    }

}