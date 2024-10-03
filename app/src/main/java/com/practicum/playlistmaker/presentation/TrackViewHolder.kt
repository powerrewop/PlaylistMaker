package com.practicum.playlistmaker.presentation

import android.icu.text.SimpleDateFormat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.SampleMusiclistBinding
import com.practicum.playlistmaker.domain.model.Track
import java.util.Locale

class TrackViewHolder(private val binding: SampleMusiclistBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Track) {

        binding.sampleBigtext.text = model.trackName
        binding.sampleSmalltext.text = model.artistName
        binding.sampleTracklen.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTime)

        Glide.with(itemView).load(model.artworkUrl100).placeholder(R.drawable.empty_image)
            .centerCrop().transform(RoundedCorners(2)).into(binding.sampleIw)
    }
}