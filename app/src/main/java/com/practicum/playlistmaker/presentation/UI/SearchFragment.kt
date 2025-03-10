package com.practicum.playlistmaker.presentation.UI

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.SearchFragmentBinding
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.TrackAdapter
import com.practicum.playlistmaker.presentation.ViewModels.SearchFragmentViewModel
import com.practicum.playlistmaker.presentation.models.SearchParamModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : Fragment() {

    private var binding: SearchFragmentBinding? = null
    private val viewModel: SearchFragmentViewModel by viewModel()

    private lateinit var recycler: RecyclerView

    private val trAdapt: TrackAdapter by inject {
        parametersOf(emptyList<Track>())
    }

    private lateinit var inputEditText: EditText

    private lateinit var problemLayout: LinearLayout
    private lateinit var problemImage: ImageView
    private lateinit var problemText: TextView
    private lateinit var buttonUpdate: Button
    private lateinit var historyText: TextView
    private lateinit var buttonHistoryClear: Button
    private lateinit var layoutProgressBar: LinearLayout
    private lateinit var layoutRV: LinearLayout
    private lateinit var clearButton: ImageView
    private lateinit var ivSearchBack: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        inputEditText = binding!!.inputEditText
        recycler = binding!!.musicList
        problemLayout = binding!!.problemLayout
        problemImage = binding!!.problemImage
        problemText = binding!!.problemText
        buttonUpdate = binding!!.buttonUpdate
        historyText = binding!!.textHistory
        buttonHistoryClear = binding!!.buttonHistoryClear
        layoutProgressBar = binding!!.progressBarLayout
        layoutRV = binding!!.rvLayout
        clearButton = binding!!.clearIcon

        trAdapt.callBackOpenPlayer = ::openPlayer //!!!!!!!!!!!!
        recycler.adapter = trAdapt


        viewModel.getSearchParamModel().observe(viewLifecycleOwner) {
            setVisibility(it)
        }

        buttonUpdate.setOnClickListener {
            viewModel.textChange(inputEditText.hasFocus(), inputEditText.text.toString())
        }

        buttonHistoryClear.setOnClickListener {
            viewModel.clearHsitory()
        }

        clearButton.setOnClickListener {

            inputEditText.setText("")
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)

            viewModel.clear()
        }

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            viewModel.setFocus(hasFocus, inputEditText.text.toString())
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.isVisible = !s.isNullOrEmpty()
                viewModel.textChange(inputEditText.hasFocus(), s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    fun setVisibility(searchParamModel: SearchParamModel) {
        when (searchParamModel) {
            is SearchParamModel.EmptyContent -> emptyContent(
                searchParamModel.tracks,
                searchParamModel.userText,
                searchParamModel.showRV
            )
            is SearchParamModel.ErrorContent -> errorContent(
                searchParamModel.tracks,
                searchParamModel.userText
            )
            is SearchParamModel.HistoryContent -> historyContent(
                searchParamModel.tracks,
                searchParamModel.userText
            )
            is SearchParamModel.LoadingContent -> loadingContent(
                searchParamModel.tracks,
                searchParamModel.userText
            )
            is SearchParamModel.SearchContent -> searchContent(
                searchParamModel.tracks,
                searchParamModel.userText
            )
            is SearchParamModel.NotFoundContent -> notFoundContent(
                searchParamModel.tracks,
                searchParamModel.userText
            )
        }
    }
    fun emptyContent(tracks: List<Track>?, userText: String, showRV: Boolean) {

        SetVisible(
            historyText_ = false,
            buttonHistoryClear_ = false,
            problemLayout_ = false,
            problemImage_ = false,
            problemText_ = false,
            buttonUpdate_ = false,
            recycler_ = showRV,
            layoutProgressBar_ = false
        )
        restoreUserData(tracks, userText)
    }
    fun errorContent(tracks: List<Track>?, userText: String) {

        SetVisible(
            historyText_ = false,
            buttonHistoryClear_ = false,
            problemLayout_ = true,
            problemImage_ = true,
            problemText_ = true,
            buttonUpdate_ = true,
            recycler_ = false,
            layoutProgressBar_ = false
        )
        problemImage.setImageResource(R.drawable.error_internet)
        problemText.setText(R.string.error_internet)
        restoreUserData(tracks, userText)
    }
    fun historyContent(tracks: List<Track>?, userText: String) {

        SetVisible(
            historyText_ = true,
            buttonHistoryClear_ = true,
            problemLayout_ = false,
            problemImage_ = false,
            problemText_ = false,
            buttonUpdate_ = false,
            recycler_ = true,
            layoutProgressBar_ = false
        )
        restoreUserData(tracks, userText)
    }
    fun loadingContent(tracks: List<Track>?, userText: String) {

        SetVisible(
            historyText_ = false,
            buttonHistoryClear_ = false,
            problemLayout_ = false,
            problemImage_ = false,
            problemText_ = false,
            buttonUpdate_ = false,
            recycler_ = false,
            layoutProgressBar_ = true
        )
        restoreUserData(tracks, userText)
    }
    fun notFoundContent(tracks: List<Track>?, userText: String) {

        SetVisible(
            historyText_ = false,
            buttonHistoryClear_ = false,
            problemLayout_ = true,
            problemImage_ = true,
            problemText_ = true,
            buttonUpdate_ = false,
            recycler_ = false,
            layoutProgressBar_ = false
        )
        problemImage.setImageResource(R.drawable.not_found)
        problemText.setText(R.string.not_found)
        restoreUserData(tracks, userText)
    }
    fun searchContent(tracks: List<Track>?, userText: String) {

        SetVisible(
            historyText_ = false,
            buttonHistoryClear_ = false,
            problemLayout_ = false,
            problemImage_ = false,
            problemText_ = false,
            buttonUpdate_ = false,
            recycler_ = true,
            layoutProgressBar_ = false
        )
        restoreUserData(tracks, userText)
    }
    fun SetVisible(
        historyText_: Boolean,
        buttonHistoryClear_: Boolean,
        problemLayout_: Boolean,
        problemImage_: Boolean,
        problemText_: Boolean,
        buttonUpdate_: Boolean,
        recycler_: Boolean,
        layoutProgressBar_: Boolean
    ) {
        historyText.isVisible = historyText_
        buttonHistoryClear.isVisible = buttonHistoryClear_
        problemLayout.isVisible = problemLayout_
        problemImage.isVisible = problemImage_
        problemText.isVisible = problemText_
        buttonUpdate.isVisible = buttonUpdate_
        recycler.isVisible = recycler_
        layoutProgressBar.isVisible = layoutProgressBar_
    }
    fun restoreUserData(tracks: List<Track>?, userText: String) {

        adapterInit(tracks)
        if (userText.isNotEmpty() && (!userText.equals(inputEditText.text.toString()))) {
            inputEditText.setText(userText)
        }
    }
    fun adapterInit(adapterListTracks: List<Track>?) {
        trAdapt!!.updateTrack(adapterListTracks!!)
        recycler.adapter?.notifyDataSetChanged()
    }

    fun openPlayer(op: Track){
        findNavController().navigate(R.id.action_searchFragment_to_playerFragment, PlayerFragment.createArgs(op))
    }

}