package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.UserTextWork

private const val USER_INPUT_TEXT = "USER_INPUT_TEXT"
private const val USER_INPUT_TEXT_DEF = ""

class UserTextWorkUseCase(private val userTextWork: UserTextWork) {
    fun save(userText: String){
        userTextWork.save(USER_INPUT_TEXT,userText)
    }
    fun load(): String{
        return userTextWork.load(USER_INPUT_TEXT,USER_INPUT_TEXT_DEF)
    }
}