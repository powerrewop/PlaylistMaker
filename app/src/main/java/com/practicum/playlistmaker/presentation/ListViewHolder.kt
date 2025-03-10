package com.practicum.playlistmaker.presentation

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.SamplePlaylistBigBinding
import com.practicum.playlistmaker.databinding.SamplePlaylistSmallBinding
import com.practicum.playlistmaker.domain.model.PlayList

class ListViewHolder(private val binding: SamplePlaylistBigBinding) : RecyclerView.ViewHolder(binding.root)  {

     fun bind(model: PlayList) {

        binding.playlistName.text = model.name
        binding.playlistCount.text = model.count

        Glide.with(itemView).load(model.image).placeholder(R.drawable.empty_image)
            .centerCrop().transform(RoundedCorners(8)).into(binding.sampleIv)
    }

}

class ListViewHolderSmall(private val binding: SamplePlaylistSmallBinding) : RecyclerView.ViewHolder(binding.root)  {

     fun bind(model: PlayList) {

        binding.playlistName.text = model.name
        binding.playlistCount.text = model.count

        Glide.with(itemView).load(model.image).placeholder(R.drawable.empty_image)
            .centerCrop().transform(RoundedCorners(2)).into(binding.sampleIv)
    }

}