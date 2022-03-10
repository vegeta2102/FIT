package jp.co.vegeta.fit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2022/02/02.
 */
@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    val keyword = MutableStateFlow<String?>(null)
    private val _wifiList = MutableLiveData<List<UserItem>>(emptyList())
    val userList: LiveData<List<UserItem>> = _wifiList

    init {
        initAdapter()
    }

    private fun initAdapter() {
        val list = mutableListOf<UserItem>().apply {
            add(UserItem("David Beckham"))
            add(UserItem("Donal Trump"))
            add(UserItem("Shinzo Abe"))
            add(UserItem("China"))
            add(UserItem("Japan"))
            add(UserItem("The United State of America"))
            add(UserItem("England"))
            add(UserItem("France"))
            add(UserItem("Germany"))
            add(UserItem("Russia"))
            add(UserItem("Vladimir Putin"))
            add(UserItem("Barack Obama"))
            add(UserItem("Hong Min Choi"))
        }
        _wifiList.postValue(list)
    }

    fun updateKeyword(text: String) {
        keyword.value = text
    }
}