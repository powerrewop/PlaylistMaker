package com.practicum.playlistmaker.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.activitys.PlayerActivity

class OptionsSearchActivity(
    private var trAdapt: TrackAdapter?,
    private var recycler: RecyclerView?,
    private val problemLayout: LinearLayout?,
    private val problemImage: ImageView,
    private val problemText: TextView,
    private val buttonUpdate: Button,
    private val historyText: TextView?,
    private val buttonHistoryClear: Button?,
    private val inputEditText: EditText?,
    private val clearButton: ImageView,
    private var userText: String,
    private val act: Activity,
    private val layoutProgressBar: LinearLayout
) {

    private var hsActive = false
   // private val handler = Handler(Looper.getMainLooper())
    fun adapterInit(adapterListTracks: List<Track>?) {
        if (trAdapt == null) {
            trAdapt = TrackAdapter(adapterListTracks!!)

            //////////Запуск формы плейера!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //calback из TrackAdapter
            trAdapt!!.onOpenElement = {track : Track, context: Context ->
                val json = Gson().toJson(track)
                val displayIntent = Intent(context, PlayerActivity::class.java)
                displayIntent.putExtra("TrackData", json)
                act.startActivity(displayIntent)}
            ////////////////////////////////////////////////////////////////////

            recycler?.adapter = trAdapt
        } else {
            trAdapt!!.updateTrack(adapterListTracks!!)
            recycler?.adapter?.notifyDataSetChanged()
        }
    }
    fun clearAdapter() {
        trAdapt!!.updateTrack(emptyList<Track>())
        recycler?.adapter?.notifyDataSetChanged()
    }
    fun visibleLayout(stat: Boolean) {
        problemLayout!!.isVisible = stat
        problemImage.isVisible = stat
        problemText.isVisible = stat
        buttonUpdate.isVisible = stat
        recycler?.isVisible = !stat
        problemText.setText(R.string.error_internet)
        problemImage.setImageResource(R.drawable.error_internet)
    }
    fun visibleLayoutEmpty(stat: Boolean) {
        problemLayout!!.isVisible = stat
        problemImage.isVisible = stat
        problemText.isVisible = stat
        buttonUpdate.isVisible = !stat
        recycler?.isVisible = !stat
        problemText.setText(R.string.not_found)
        problemImage.setImageResource(R.drawable.not_found)
    }
    fun showHistory(isActive: Boolean, historyTrack: List<Track>?) {

        if (isActive) {
            // historyTrack = getHistorySearch((applicationContext as App))
            if ((historyTrack!!.isNotEmpty()) && (historyTrack!!.size > 0)){
                historyText!!.isVisible = true
                buttonHistoryClear!!.isVisible = true
                adapterInit(historyTrack)
                visibleLayout(false)
                visibleLayoutEmpty(false)
            } else {
                hideHistoryElements()
            }
        } else {
            hideHistoryElements()
        }
    }
    fun hideHistoryElements() {
        historyText!!.isVisible = false
        buttonHistoryClear!!.isVisible = false
        trAdapt?.updateTrack(emptyList<Track>())
        trAdapt?.notifyDataSetChanged()
    }
    fun clear(){
        inputEditText?.setText("")
        val inputMethodManager = act.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText?.windowToken, 0)
        problemLayout?.isVisible = false
        recycler?.isVisible = true
        layoutProgressBar.isVisible = false
    }
    @SuppressLint("SuspiciousIndentation")
    fun setFocus(hasFocus: Boolean, tracks: List<Track>?){
        layoutProgressBar.isVisible = false
        var historyActive = false;
            if (hasFocus && inputEditText!!.text.isEmpty()) {
                historyActive = true
            }else{
                historyActive = false
            }

        if(inputEditText!!.text.isEmpty()){
            hsActive = true
        }else
        {
            hsActive = false
        }

        showHistory(historyActive, tracks)

    }
    fun textChange(s: CharSequence?, tracks: List<Track>){
        clearButton.isVisible = !s.isNullOrEmpty()
        userText = s.toString()

        //Focus
        val historyActive =
            if (inputEditText!!.hasFocus() && s?.isEmpty() == true) true else false

        showHistory(historyActive, tracks)
    }
    fun postSearch(resultCode: Int, tracks: List<Track>?) {

        layoutProgressBar.isVisible = false
        if (!hsActive) {

            if ((resultCode == 200) && ((tracks == null) || (tracks.isEmpty()))) {
                hideHistoryElements()
                visibleLayout(false)
                visibleLayoutEmpty(true)
            } else if (resultCode != 200) {
                hideHistoryElements()
                visibleLayoutEmpty(false)
                visibleLayout(true)
            } else {
                adapterInit(tracks)
                historyText!!.isVisible = false
                buttonHistoryClear!!.isVisible = false
                visibleLayoutEmpty(false)
                visibleLayout(false)
            }
        }
    }
    fun setHsActive(){
        layoutProgressBar.isVisible = false
        visibleLayout(false)
        if(inputEditText!!.text.isEmpty()){
            hsActive = true
        }else
        {
            hsActive = false
            //handler.postDelayed({ layoutProgressBar.isVisible = true }, 2000L)

        }

    }
}