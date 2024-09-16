package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.domain.model.Track
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var inputEditText: EditText? = null
    private var userText: String = USER_INPUT_TEXT_DEF
    private var recycler: RecyclerView? = null
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var problemLayout: LinearLayout
    private lateinit var problemImage: ImageView
    private lateinit var problemText: TextView
    private lateinit var buttonUpdate: Button
    private lateinit var layoutProgressBar: LinearLayout
    private lateinit var layoutRV: LinearLayout

    private var tracks: MutableList<Track>? = mutableListOf()
    private var trAdapt: TrackAdapter? = null
    private var historyText: TextView? = null
    private var buttonHistoryClear: Button? = null

    private var historyTrack: MutableList<Track>? = mutableListOf()

    private var baseUrlIyunes = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrlIyunes)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ItunesApiService::class.java)

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        inputEditText = findViewById(R.id.inputEditText)
        recycler = findViewById(R.id.musicList)

        problemLayout = findViewById(R.id.problem_layout)
        problemImage = findViewById(R.id.problem_image)
        problemText = findViewById(R.id.problem_text)
        buttonUpdate = findViewById(R.id.button_update)
        historyText = findViewById(R.id.text_history)
        buttonHistoryClear = findViewById(R.id.button_history_clear)
        layoutProgressBar = findViewById(R.id.progressBar_layout)
        layoutRV = findViewById(R.id.rv_layout)


        val ivSearchBack = findViewById<ImageView>(R.id.iv_searchBack)
        ivSearchBack.setOnClickListener {
            finish()

        }

        buttonUpdate.setOnClickListener {

            startSearch(userText)
            visibleLayout(false)

        }

        buttonHistoryClear!!.setOnClickListener {

            clearHistory((applicationContext as App))
            hideHistoryElements()

        }


        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener {

            inputEditText?.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText?.windowToken, 0)
            clearAdapter()
            problemLayout.isVisible = false
        }
        //Focus
        inputEditText?.setOnFocusChangeListener { view, hasFocus ->
            val historyActive = if (hasFocus && inputEditText!!.text.isEmpty()) true else false
            showHistory(historyActive)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                clearButton.isVisible = !s.isNullOrEmpty()

                userText = s.toString()

                //Focus
                val historyActive =
                    if (inputEditText!!.hasFocus() && s?.isEmpty() == true) true else false
                showHistory(historyActive)

                startSearch(userText)

            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        inputEditText?.addTextChangedListener(simpleTextWatcher)

        recycler?.layoutManager = LinearLayoutManager(this)


    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(USER_INPUT_TEXT, userText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        userText = savedInstanceState.getString(USER_INPUT_TEXT, USER_INPUT_TEXT_DEF)
        inputEditText?.setText(userText)

    }

    companion object {
        private const val USER_INPUT_TEXT = "USER_INPUT_TEXT"
        private const val USER_INPUT_TEXT_DEF = ""
        private const val USER_INPUT_DELAY = 2000L
    }

    private fun visibleLayout(stat: Boolean) {
        problemLayout.isVisible = stat
        problemImage.isVisible = stat
        problemText.isVisible = stat
        buttonUpdate.isVisible = stat
        recycler?.isVisible = !stat

        problemText.setText(R.string.error_internet)
        problemImage.setImageResource(R.drawable.error_internet)

    }

    private fun visibleLayoutEmpty(stat: Boolean) {
        problemLayout.isVisible = stat
        problemImage.isVisible = stat
        problemText.isVisible = stat
        buttonUpdate.isVisible = !stat
        recycler?.isVisible = !stat

        problemText.setText(R.string.not_found)
        problemImage.setImageResource(R.drawable.not_found)
    }

    private fun startSearch(searchString: String) {

        if (searchString.isNotEmpty() && searchString.length > 2) {
            val searchRunnable = Runnable {

                layoutProgressBar.isVisible = true
                layoutRV.isVisible = false
                problemLayout.isVisible = false

                iTunesService.search(searchString)
                    .enqueue(object : retrofit2.Callback<ItunesDataModel> {
                        override fun onResponse(
                            call: retrofit2.Call<ItunesDataModel>,
                            response: retrofit2.Response<ItunesDataModel>
                        ) {

                            layoutProgressBar.isVisible = false
                            layoutRV.isVisible = true
                            problemLayout.isVisible = true

                            if (response.isSuccessful) {
                                tracks = response.body()?.results

                                if (tracks != null) {

                                    if (tracks!!.isNotEmpty()) {

                                        adapterInit(tracks)

                                        visibleLayout(false)

                                    } else {
                                        visibleLayoutEmpty(true)
                                    }

                                }

                            } else {

                                visibleLayout(true)

                            }


                        }

                        override fun onFailure(
                            call: retrofit2.Call<ItunesDataModel>,
                            t: Throwable
                        ) {

                            visibleLayout(true)
                        }
                    })

            }

            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, USER_INPUT_DELAY)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearAdapter() {
        tracks?.clear()
        recycler?.adapter?.notifyDataSetChanged()
    }

    private fun adapterInit(adapterListTracks: MutableList<Track>?) {

        if (trAdapt == null) {
            trAdapt = TrackAdapter(adapterListTracks!!)
            recycler?.adapter = trAdapt
        } else {
            trAdapt!!.updateTrack(adapterListTracks!!)
            recycler?.adapter?.notifyDataSetChanged()
        }
    }

    private fun showHistory(isActive: Boolean) {

        if (isActive) {

            historyTrack = getHistorySearch((applicationContext as App))

            if ((historyTrack!!.isNotEmpty()) && (historyTrack!!.size > 0)){
                historyText!!.isVisible = true
                buttonHistoryClear!!.isVisible = true

                adapterInit(historyTrack)

            } else {
                hideHistoryElements()
            }

        } else {

            hideHistoryElements()

        }

    }

    private fun hideHistoryElements() {

        historyText!!.isVisible = false
        buttonHistoryClear!!.isVisible = false
        trAdapt?.updateTrack(emptyList<Track>())
        trAdapt?.notifyDataSetChanged()
    }

}