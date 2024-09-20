package com.practicum.playlistmaker.presentation.activitys

import android.os.Bundle
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
import com.practicum.playlistmaker.creator.SearchUseCaseCreator
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.storage.UserTextWorkImpl
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.UserTextWorkUseCase
import com.practicum.playlistmaker.presentation.OptionsSearchActivity
import com.practicum.playlistmaker.presentation.TrackAdapter

private const val USER_INPUT_TEXT_DEF = ""
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

    private lateinit var userTextWorkImpl: UserTextWorkImpl
    private lateinit var userTextWorkUseCase: UserTextWorkUseCase

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

        //////////////////////////////////////////
        val searchUseCaseCreator = SearchUseCaseCreator(applicationContext as App)
        val creatorUserTextWorkModel = searchUseCaseCreator.getUserTextWork()
        userTextWorkImpl = creatorUserTextWorkModel.userTextWorkImpl
        userTextWorkUseCase = creatorUserTextWorkModel.userTextWorkUseCase
        //////////////////////////////////////////

        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        /////////////////////////
        val getHistorySearchUseCase = searchUseCaseCreator.getHistorySearchUseCase()
        /////////////////////////

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
            this,
            layoutProgressBar
        )
        /////////////////////////

        val creatorSearchModel = searchUseCaseCreator.getSearch("https://itunes.apple.com")
        val runSearch = creatorSearchModel.runSearch
        //Это код который будет выполнен когда поток вернет результат!!!!
        runSearch.onBack = { lt: List<Track>?, code: Int ->
            runOnUiThread {
                optionsSearchActivity.postSearch(code, lt)
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        val getSearchUseCase = creatorSearchModel.getSearchUseCase

        /////////////////////////
        val clearHistoryUseCase = searchUseCaseCreator.getClearHistory()
        ////////////////////////

        val ivSearchBack = findViewById<ImageView>(R.id.iv_searchBack)
        ivSearchBack.setOnClickListener {
            finish()
        }

        buttonUpdate.setOnClickListener {
            getSearchUseCase.search(inputEditText?.text.toString())
            optionsSearchActivity.visibleLayout(false)
            layoutProgressBar.isVisible = true
        }

        buttonHistoryClear!!.setOnClickListener {
            clearHistoryUseCase.clearHistory()
            optionsSearchActivity.hideHistoryElements()
        }


        clearButton.setOnClickListener {
            optionsSearchActivity.clear()
            optionsSearchActivity.showHistory(true, getHistorySearchUseCase.getHistorySearch())
        }

        inputEditText?.setOnFocusChangeListener { view, hasFocus ->
            optionsSearchActivity.setFocus(hasFocus, getHistorySearchUseCase.getHistorySearch())
        }

            val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                optionsSearchActivity.textChange(s, getHistorySearchUseCase.getHistorySearch())

                optionsSearchActivity.setHsActive()
                getSearchUseCase.search(s.toString())

            }
            override fun afterTextChanged(s: Editable?) {

            }
        }

        inputEditText?.addTextChangedListener(simpleTextWatcher)
        recycler?.layoutManager = LinearLayoutManager(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        userTextWorkImpl.bundle = outState
        userTextWorkUseCase.save(userText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        userTextWorkImpl.bundle = savedInstanceState
        userText = userTextWorkUseCase.load()
    }
}