package jp.co.vegeta.medium.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by vegeta on 2021/03/12.
 */
class NextScreenViewModel @ViewModelInject constructor() : ViewModel() {

    private val _closeRequest = MutableLiveData<Unit>()
    val closeRequest: LiveData<Unit> = _closeRequest

    private val _openRequest = MutableLiveData<Unit>()
    val openRequest: LiveData<Unit> = _openRequest

    fun close() {
        _closeRequest.postValue(Unit)
    }

    fun open() {
        _openRequest.postValue(Unit)
    }
}