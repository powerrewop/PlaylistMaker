package com.practicum.playlistmaker.presentation.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.presentation.ViewModels.PlayerViewModel
import org.koin.core.parameter.parametersOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {

    private lateinit var  viewModel: PlayerViewModel
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val arguments = getIntent().getExtras()
        val json = arguments?.getString("TrackData")

        val tempVM: PlayerViewModel by viewModel {
            parametersOf(json)
        }

        viewModel = tempVM

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val iv_back = binding.ivPlayerBack
        val iv_banner = binding.ivPlayerBanner
        val tv_trackName = binding.tvNameTrack
        val tv_trackAlbum = binding.tvNameAlbum
        val iv_buttonAdd = binding.ivButtonAdd
        val iv_buttonPlay = binding.ivButtonPlay
        val iv_buttonLike = binding.ivButtonLike
        val tv_trackLenPlay = binding.tvTrackLenPlay
        val tv_trackLenInfo = binding.tvTrackLenData
        val tv_trackAlbumHead = binding.tvTrackAlbum
        val tv_trackAlbumInfo = binding.tvTrackAlbumData
        val tv_trackYearInfo = binding.tvTrackYearData
        val tv_trackGenreInfo = binding.tvTrackGenreData
        val tv_trackCountryInfo = binding.tvTrackCountryData

        viewModel.getDataTrack().observe(this) {

            Glide.with(this).load(it.artworkUrl100)
                .placeholder(R.drawable.empty_image)
                .centerCrop().transform(RoundedCorners(8)).into(iv_banner)

            tv_trackName.text = it.trackName
            tv_trackAlbum.text = it.collectionName
            tv_trackAlbumInfo.text = it.collectionName
            tv_trackLenPlay.text = it.timerText
            tv_trackLenInfo.text = it.trackTime
            tv_trackYearInfo.text = it.releaseDate
            tv_trackGenreInfo.text = it.primaryGenreName
            tv_trackCountryInfo.text = it.country

            tv_trackAlbum.isVisible = it.trackAlbumVisible
            tv_trackAlbumHead.isVisible = it.trackAlbumVisible
            tv_trackAlbumInfo.isVisible = it.trackAlbumVisible

            if(it.isPlay){
                iv_buttonPlay.setImageResource(R.drawable.button_pause_track)
            }else{
                iv_buttonPlay.setImageResource(R.drawable.button_play_track)
            }

            if (it.isFav){
                iv_buttonLike.setImageResource(R.drawable.button_like_track_on)
            }else{
                iv_buttonLike.setImageResource(R.drawable.button_like_track)
            }
        }

        iv_back.setOnClickListener {
            finish()
        }

        iv_buttonPlay.setOnClickListener {
            viewModel.buttonPlay()
        }

        iv_buttonLike.setOnClickListener {
            viewModel.buttonFavPressed()
        }
    }
    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.pausePlayer()
    }
}