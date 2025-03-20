package com.practicum.playlistmaker.presentation.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ViewPlaylistFragmentBinding
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.ListsAdapter
import com.practicum.playlistmaker.presentation.TrackAdapter
import com.practicum.playlistmaker.presentation.ViewModels.ViewPlayListFragmentViewModel
import com.practicum.playlistmaker.presentation.models.ViewPlayListDataModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent

class ViewPlayListFragment()  : Fragment() {

    private var binding: ViewPlaylistFragmentBinding? = null
    private lateinit var viewModel: ViewPlayListFragmentViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var peekHeightBH: Int = 0

    private val trAdapt: TrackAdapter by inject {
        parametersOf(emptyList<Track>())
    }

    private val trAdaptList: ListsAdapter by inject {
        parametersOf(emptyList<PlayList>())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = ViewPlaylistFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(binding!!.bs)
        peekHeightBH = bottomSheetBehavior.peekHeight

        /////////////////////
        trAdapt.isFavForm = false
        trAdapt.callBackOpenPlayer = ::openPlayer //!!!!!!!!!!!!
        trAdapt.callBackLongClick = ::longClick //!!!!!!!!!!!!
        binding!!.rv.adapter = trAdapt
        /////////////////////
        trAdaptList.typeH = 2
        trAdaptList.callBackUserSelect = ::userSelectShoice
        binding!!.rv2.adapter = trAdaptList
        binding!!.rv2.layoutManager = LinearLayoutManager(requireContext())
        /////////////////////

        binding!!.rv.layoutManager = LinearLayoutManager(requireContext())

        val gsonList = requireArguments().getString(OPEN_LIST)

        val tempVM: ViewPlayListFragmentViewModel by viewModel {
            parametersOf(gsonList)
        }

        viewModel = tempVM

        viewModel.callbackEmptyPlayList = ::showMessageEmptyPlayList

        viewModel.getViewPlayListDataModel().observe(viewLifecycleOwner) {
            setVisibility(it)
        }

        binding!!.ivMore.setOnClickListener {
            viewModel.setIsChoeseUser(true)
        }

        binding!!.ivPlBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding!!.ivShare.setOnClickListener {
           sharePlayList()
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        viewModel.setIsChoeseUser(false)
                        binding!!.overlay.isVisible = false
                    }
                    else -> {

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                findNavController().navigateUp()
            }
        })

    }

    override fun onResume() {
        super.onResume()

        viewModel.updateData()

    }

    private fun setVisibility(md: ViewPlayListDataModel){

        if (md.isUserChoise){
            binding!!.overlay.isVisible = true
            binding!!.rv.isVisible = false
            binding!!.rv2.isVisible = true
            bottomSheetBehavior.isHideable = true
            bottomSheetBehavior.peekHeight = peekHeightBH + 200
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }else{
            binding!!.rv.isVisible = true
            binding!!.rv2.isVisible = false
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.peekHeight = peekHeightBH

            if (md.listTrack?.isEmpty()?:true){
                bottomSheetBehavior.isHideable = true
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            }else{
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                bottomSheetBehavior.isHideable = false
            }
        }

        binding!!.nameTextView.text = md.name
        binding!!.nameTextDesc.text = md.desc
        binding!!.trackCount.text = md.count
        binding!!.allTime.text = md.allMinut

        Glide.with(this).load(md.image).placeholder(R.drawable.empty_image)
            .centerCrop().transform(RoundedCorners(8)).into(binding!!.ivImageAlbum)

        adapterInit(md.listTrack)
        adapterInitList(md.listPlayList)
    }

    fun openPlayer(op: Track){
        findNavController().navigate(R.id.action_viewPlayListFragment_to_playerFragment, PlayerFragment.createArgs(op))
    }

    fun longClick(op: Track){

        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.dialog_deltrack_title))
            .setNegativeButton(getString(R.string.dialog_no)) { dialog, which ->
            }
            .setPositiveButton(getString(R.string.dialog_yes)) { dialog, which ->
                viewModel.delTrack(op)
            }
            .show()
    }

    fun adapterInit(adapterListTracks: List<Track>?) {
        trAdapt!!.updateTrack(adapterListTracks!!)
        trAdapt.notifyDataSetChanged()
    }

    fun adapterInitList(listLists: List<PlayList>?) {
        trAdaptList.updateLists(listLists!!)
        trAdaptList.notifyDataSetChanged()
    }

    fun userSelectShoice(ch: Int){

        if (ch == 1){
            sharePlayList()
        }

        if (ch == 2){
            viewModel.setIsChoeseUser(false)
            binding!!.overlay.isVisible = false
           findNavController().navigate(R.id.action_viewPlayListFragment_to_createListFragment,CreateListFragment.createArgs(viewModel.getTrackList()))
        }

        if (ch == 3){
            viewModel.setIsChoeseUser(false)
            binding!!.overlay.isVisible = false

            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.dialog_delplaylist_text))
                .setNegativeButton(getString(R.string.dialog_no)) { dialog, which ->
                }
                .setPositiveButton(getString(R.string.dialog_yes)) { dialog, which ->
                    viewModel.delPlayList(::deleteFinish)
                }
                .show()
        }
    }

    fun deleteFinish(){
        findNavController().navigateUp()
    }

    fun sharePlayList(){

        viewModel.setIsChoeseUser(false)
        binding!!.overlay.isVisible = false

        if(!viewModel.listsTrackIsEmpty()) {
            viewModel.sharePlayList()
        }else{
            Toast.makeText(
                requireContext(),
                getString(R.string.message_share),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun showMessageEmptyPlayList() {
        Toast.makeText(
            requireContext(),
            getString(R.string.message_playlist_empty),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {

        const val OPEN_LIST = "OPEN_LIST"

        fun createArgs(playList: PlayList): Bundle {
            val gson: Gson = KoinJavaComponent.getKoin().get(Gson::class, null)
            val gsonList = gson.toJson(playList)
            return bundleOf(OPEN_LIST to gsonList)
        }
    }

}