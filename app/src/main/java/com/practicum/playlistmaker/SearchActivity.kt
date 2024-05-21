package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val ivSearchBack = findViewById<ImageView>(R.id.iv_searchBack)
        ivSearchBack.setOnClickListener {
            val displayIntent = Intent(this, MainActivity::class.java)
            startActivity(displayIntent)
        }

        val inputEditText = findViewById<EditText>(R.id.inputEditText)

        val clearButton = findViewById<ImageView>(R.id.clearIcon)
            clearButton.setOnClickListener {
                inputEditText.setText("")
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.isNullOrEmpty()){
                    clearButton.visibility = View.GONE
                }else{
                    clearButton.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

    }
}