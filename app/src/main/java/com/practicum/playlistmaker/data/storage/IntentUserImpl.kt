package com.practicum.playlistmaker.data.storage

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.presentation.activitys.SearchActivityNew
import com.practicum.playlistmaker.domain.storage.interfaces.IntentUser
import com.practicum.playlistmaker.presentation.activitys.MediaActivity
import com.practicum.playlistmaker.presentation.activitys.SettingsActivity

class IntentUserImpl(private val context: Context) : IntentUser {
    override fun getSend(): Intent {
        val actionShare = Intent(Intent.ACTION_SEND)
        actionShare.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.link_YP))
        actionShare.type = "text/x-uri"
        val shareLink = Intent.createChooser(actionShare, null)
        return shareLink
    }

    override fun getSendTo(): Intent {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.student_email)))
        emailIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.text2))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.text1))
        return emailIntent
    }

    override fun getView(): Intent {
        val url = Uri.parse(context.getString(R.string.legal_YP))
        val intent = Intent(Intent.ACTION_VIEW, url)
        return intent
    }

    override fun getSearchActivity(): Intent {
        return Intent(context, SearchActivityNew::class.java)
    }

    override fun getMediaActivity(): Intent {
        return Intent(context, MediaActivity::class.java)
    }

    override fun getSettingsActivity(): Intent {
        return Intent(context, SettingsActivity::class.java)
    }
}