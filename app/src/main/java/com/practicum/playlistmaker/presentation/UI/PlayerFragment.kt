package com.practicum.playlistmaker.presentation.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlayerFragmentBinding
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.ListsAdapter
import com.practicum.playlistmaker.presentation.ViewModels.PlayerFragmentViewModel
import com.practicum.playlistmaker.presentation.models.SelectPlayListModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

class PlayerFragment(): Fragment() {

    private var binding: PlayerFragmentBinding? = null
    private lateinit var viewModel: PlayerFragmentViewModel

    private lateinit var recycler: RecyclerView

    private val trAdapt: ListsAdapter by inject {
        parametersOf(emptyList<Track>())
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        binding = PlayerFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gsonTrack = requireArguments().getString(OPEN_TRACK)

        val tempVM: PlayerFragmentViewModel by viewModel {
            parametersOf(gsonTrack)
        }

        viewModel = tempVM

        val iv_back = binding!!.ivPlayerBack
        val iv_banner = binding!!.ivPlayerBanner
        val tv_trackName = binding!!.tvNameTrack
        val tv_trackAlbum = binding!!.tvNameAlbum
        val iv_buttonAdd = binding!!.ivButtonAdd
        val iv_buttonPlay = binding!!.ivButtonPlay
        val iv_buttonLike = binding!!.ivButtonLike
        val tv_trackLenPlay = binding!!.tvTrackLenPlay
        val tv_trackLenInfo = binding!!.tvTrackLenData
        val tv_trackAlbumHead = binding!!.tvTrackAlbum
        val tv_trackAlbumInfo = binding!!.tvTrackAlbumData
        val tv_trackYearInfo = binding!!.tvTrackYearData
        val tv_trackGenreInfo = binding!!.tvTrackGenreData
        val tv_trackCountryInfo = binding!!.tvTrackCountryData

        viewModel.getDataTrack().observe(viewLifecycleOwner) {

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
            findNavController().navigateUp()
        }

        iv_buttonPlay.setOnClickListener {
            viewModel.buttonPlay()
        }

        iv_buttonLike.setOnClickListener {
            viewModel.buttonFavPressed()
        }

        iv_buttonAdd.setOnClickListener {
            viewModel.showSelectPlayLists()
        }

        ///////////////////////////

        bottomSheetBehavior = BottomSheetBehavior.from(binding!!.bs)

        viewModel.getSelectPlayLists().observe(viewLifecycleOwner) {
            showSelectPlayLists(it)
        }


        trAdapt.typeH = 1
        trAdapt.callBack = ::onChoisePlayList
        recycler = binding!!.rv
        recycler.adapter = trAdapt
        recycler.layoutManager = LinearLayoutManager(requireContext())

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        viewModel.hideSelectPlayLists()
                    }
                    else -> {

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding!!.buttonNewPlaylist.setOnClickListener {

            viewModel.hideSelectPlayLists()
            findNavController().navigate(R.id.action_playerFragment_to_createListFragment)

        }
        ///////////////////////////



    }

    override fun onPause() {
        super.onPause()

        viewModel.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.pausePlayer()
    }

    fun adapterInit(listLists: List<PlayList>) {
        trAdapt.updateLists(listLists)
        recycler.adapter?.notifyDataSetChanged()
    }

    fun showSelectPlayLists(selectPlayListModel: SelectPlayListModel) {

        if (selectPlayListModel.showBottomSheet) {
            binding!!.overlay.isVisible = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            selectPlayListModel.listLists?.let { adapterInit(it) }
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            binding!!.overlay.isVisible = false
        }

    }

    fun onChoisePlayList(idList: Long, name: String){

        viewModel.addToPlayList(idList, name, ::showToast)

    }

    fun showToast(isCreate: Boolean, name: String){

        if (isCreate) {
            viewModel.hideSelectPlayLists()
            Toast.makeText(requireContext(), "Добавлено в плейлист $name", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Трек уже добавлен в плейлист $name", Toast.LENGTH_SHORT).show()
        }

    }
    companion object {

        const val OPEN_TRACK = "OPEN_TRACK"
        fun newInstance(gsonTrack: String) = PlayerFragment().apply {
            arguments = bundleOf(OPEN_TRACK to gsonTrack)
        }

        fun createArgs(track: Track): Bundle {
            val gson: Gson = getKoin().get(Gson::class, null)
            val gsonTrack = gson.toJson(track)
            return bundleOf(OPEN_TRACK to gsonTrack)
        }
    }
}