package com.practicum.playlistmaker.presentation.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.domain.usecase.MediaplayerUseCase
import com.practicum.playlistmaker.presentation.OptionsPlayerActivity

class PlayerActivity : AppCompatActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var iv_banner: ImageView
    private lateinit var tv_trackName: TextView
    private lateinit var tv_trackAlbum: TextView
    private lateinit var iv_buttonAdd: ImageView
    private lateinit var iv_buttonPlay: ImageView
    private lateinit var iv_buttonLike: ImageView
    private lateinit var tv_trackLenPlay: TextView
    private lateinit var tv_trackLenInfo: TextView
    private lateinit var tv_trackAlbumHead: TextView
    private lateinit var tv_trackAlbumInfo: TextView
    private lateinit var tv_trackYearInfo: TextView
    private lateinit var tv_trackGenreInfo: TextView
    private lateinit var tv_trackCountryInfo: TextView
    private lateinit var mediaplayerUseCase: MediaplayerUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val paramDataUseCase = Creator.getParamDataUseCase()

        val arguments = getIntent().getExtras()
        val json = arguments?.getString("TrackData")

        val track = paramDataUseCase.getData(json?:"")

        val setPlayImage = {
            iv_buttonPlay.setImageResource(R.drawable.button_play_track)
        }

        val setPauseImage = {
            iv_buttonPlay.setImageResource(R.drawable.button_pause_track)
        }

        val setTimer = {
            textTimer: String -> tv_trackLenPlay.text = textTimer
        }

        mediaplayerUseCase = Creator.getMediaplayerUseCase()

        mediaplayerUseCase.preparePlayer(track.previewUrl, setPlayImage, setPauseImage, setTimer)

        iv_back = findViewById<ImageView>(R.id.iv_playerBack)
        iv_banner = findViewById<ImageView>(R.id.iv_playerBanner)
        tv_trackName = findViewById<TextView>(R.id.tv_nameTrack)
        tv_trackAlbum = findViewById<TextView>(R.id.tv_nameAlbum)
        iv_buttonAdd = findViewById<ImageView>(R.id.iv_buttonAdd)
        iv_buttonPlay = findViewById<ImageView>(R.id.iv_buttonPlay)
        iv_buttonLike = findViewById<ImageView>(R.id.iv_buttonLike)
        tv_trackLenPlay = findViewById<TextView>(R.id.tv_track_len_play)
        tv_trackLenInfo = findViewById<TextView>(R.id.tv_track_len_data)
        tv_trackAlbumHead = findViewById<TextView>(R.id.tv_track_album)
        tv_trackAlbumInfo = findViewById<TextView>(R.id.tv_track_album_data)
        tv_trackYearInfo = findViewById<TextView>(R.id.tv_track_year_data)
        tv_trackGenreInfo = findViewById<TextView>(R.id.tv_track_genre_data)
        tv_trackCountryInfo = findViewById<TextView>(R.id.tv_track_country_data)

        iv_back.setOnClickListener {
            finish()
        }

        iv_buttonPlay.setOnClickListener {
            mediaplayerUseCase.playbackControl()
        }

        Glide.with(this).load(track.artworkUrl100)
            .placeholder(R.drawable.empty_image)
            .centerCrop().transform(RoundedCorners(8)).into(iv_banner)

        tv_trackName.text = track.trackName

        val optionsPlayerActivity = OptionsPlayerActivity()
        optionsPlayerActivity.setVisibility(tv_trackAlbum, tv_trackAlbumHead, tv_trackAlbumInfo, track.collectionName)

        tv_trackAlbum.text = track.collectionName
        tv_trackAlbumInfo.text = track.collectionName

        tv_trackLenPlay.text = getString(R.string.track_timer_start)
        tv_trackLenInfo.text = optionsPlayerActivity.getTrackTime(track.trackTime)
        tv_trackYearInfo.text = optionsPlayerActivity.getYear(track.releaseDate)
        tv_trackGenreInfo.text = track.primaryGenreName
        tv_trackCountryInfo.text = track.country
    }
    override fun onPause() {
        super.onPause()
        mediaplayerUseCase.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaplayerUseCase.closePlayer()
    }
}