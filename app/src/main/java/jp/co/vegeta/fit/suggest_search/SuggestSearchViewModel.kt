package jp.co.vegeta.fit.suggest_search

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by vegeta on 2022/05/18.
 */
@HiltViewModel
class SuggestSearchViewModel @Inject constructor() : ViewModel(), LifecycleObserver {

    private val _userList = MutableLiveData<List<String>>()
    val userList: LiveData<List<String>> = _userList

    fun init() {
        _userList.postValue(
            listOf(
                "Vu",
                "Minh",
                "Hoang",
                "Le",
                "Thi",
                "Lan",
                "Huong",
                "Hoi",
                "Beo"
            )
        )
    }
}