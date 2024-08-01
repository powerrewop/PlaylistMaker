package com.practicum.playlistmaker

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.util.Locale

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

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

        val arguments  = getIntent().getExtras();
        val json = arguments?.getString("TrackData")
        val track = Gson().fromJson(json, Track::class.java)

        iv_back.setOnClickListener{
            finish()
        }



        Glide.with(this).load(track.artworkUrl100?.replaceAfterLast('/',"512x512bb.jpg")).placeholder(R.drawable.empty_image)
            .centerCrop().transform(RoundedCorners(8)).into(iv_banner)

        tv_trackName.text = track.trackName

        if(track.collectionName?.isEmpty() == true){
            tv_trackAlbum.isVisible = false
            tv_trackAlbumHead.isVisible = false
            tv_trackAlbumInfo.isVisible = false
        }else{
            tv_trackAlbum.isVisible = true
            tv_trackAlbumHead.isVisible = true
            tv_trackAlbumInfo.isVisible = true
            tv_trackAlbum.text = track.collectionName
            tv_trackAlbumInfo.text = track.collectionName
        }

        tv_trackLenPlay.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime)
        tv_trackLenInfo.text = tv_trackLenPlay.text
        tv_trackYearInfo.text = track.releaseDate.substring(0,4)
        tv_trackGenreInfo.text = track.primaryGenreName
        tv_trackCountryInfo.text = track.country

    }
}