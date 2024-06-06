package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private var inputEditText: EditText? = null
    private var userText: String = USER_INPUT_TEXT_DEF
    private var recycler: RecyclerView? = null

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        inputEditText = findViewById(R.id.inputEditText)
        recycler = findViewById<RecyclerView>(R.id.musicList)

        val ivSearchBack = findViewById<ImageView>(R.id.iv_searchBack)
        ivSearchBack.setOnClickListener {
            finish()

        }

        val clearButton = findViewById<ImageView>(R.id.clearIcon)
            clearButton.setOnClickListener {
                inputEditText?.setText("")
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(inputEditText?.windowToken, 0)
            }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                clearButton.isVisible = !s.isNullOrEmpty()

                userText = s.toString()

            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        inputEditText?.addTextChangedListener(simpleTextWatcher)

        recycler?.layoutManager = LinearLayoutManager(this)
        recycler?.adapter = TrackAdapter(getTestVal())

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
    }

}