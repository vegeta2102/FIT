package com.vegeta.voicerecoginize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by vegeta on 2022/06/23.
 */
@HiltViewModel
class VoiceRecognizeViewModel @Inject constructor() : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun updateMessage(s: String) {
        _message.postValue(s)
    }
}