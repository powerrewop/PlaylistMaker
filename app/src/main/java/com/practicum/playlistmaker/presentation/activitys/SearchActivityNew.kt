package com.practicum.playlistmaker.presentation.activitys

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.presentation.OptionsSearchActivity
import com.practicum.playlistmaker.presentation.TrackAdapter

private const val USER_INPUT_TEXT_DEF = ""
private const val USER_DELAY_INPUT = 2000L
private const val USER_INPUT_TEXT = "USER_INPUT_TEXT"

class SearchActivityNew : AppCompatActivity() {

    private var inputEditText: EditText? = null
    private var userText: String = USER_INPUT_TEXT_DEF
    private var recycler: RecyclerView? = null

    private lateinit var problemLayout: LinearLayout
    private lateinit var problemImage: ImageView
    private lateinit var problemText: TextView
    private lateinit var buttonUpdate: Button
    private lateinit var layoutProgressBar: LinearLayout
    private lateinit var layoutRV: LinearLayout

    private var historyText: TextView? = null
    private var buttonHistoryClear: Button? = null

    private var trAdapt: TrackAdapter? = null

    private var runSearchList: MutableList<Runnable> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val handler = Handler(Looper.getMainLooper())

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

        val historySearchInteractor = Creator.getHistorySearchInteractor()

        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        val optionsSearchActivity = OptionsSearchActivity(
            trAdapt,
            recycler,
            problemLayout,
            problemImage,
            problemText,
            buttonUpdate,
            historyText,
            buttonHistoryClear,
            inputEditText,
            clearButton,
            userText,
            layoutProgressBar
        )

        val loadTracksUseCase = Creator.getLoadTracksUseCase()

        val ivSearchBack = findViewById<ImageView>(R.id.iv_searchBack)
        ivSearchBack.setOnClickListener {
            finish()
        }

        buttonUpdate.setOnClickListener {

            loadTracksUseCase.load(inputEditText?.text.toString(), {
                    tracks ->
                layoutProgressBar.isVisible = false
                optionsSearchActivity.postSearch(200, tracks)

            }, {
                layoutProgressBar.isVisible = false
                optionsSearchActivity.postSearch(400, emptyList())
            })

            optionsSearchActivity.visibleLayout(false)
            layoutProgressBar.isVisible = true
        }

        buttonHistoryClear!!.setOnClickListener {
            historySearchInteractor.clear()
            optionsSearchActivity.hideHistoryElements()
        }

        clearButton.setOnClickListener {
            optionsSearchActivity.clear()
            optionsSearchActivity.showHistory(true, historySearchInteractor.load())
        }

        inputEditText?.setOnFocusChangeListener { view, hasFocus ->
            optionsSearchActivity.setFocus(hasFocus, historySearchInteractor.load())
        }

            val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                optionsSearchActivity.textChange(s, historySearchInteractor.load())

                optionsSearchActivity.setHsActive()

                    val runSearch = Runnable {
                        layoutProgressBar.isVisible = true
                        loadTracksUseCase.load(s.toString(), {
                            tracks ->
                        layoutProgressBar.isVisible = false
                        optionsSearchActivity.postSearch(200, tracks)
                    }, {
                        layoutProgressBar.isVisible = false
                        optionsSearchActivity.postSearch(400, emptyList())
                    })}

                runSearchList.forEach { handler.removeCallbacks(it) }
                runSearchList.clear()
                runSearchList.add(runSearch)

                handler.postDelayed(runSearch, USER_DELAY_INPUT)
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
    }
}