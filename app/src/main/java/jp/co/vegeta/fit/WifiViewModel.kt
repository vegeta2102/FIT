package jp.co.vegeta.fit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by vegeta on 2022/02/02.
 */
class WifiViewModel @ViewModelInject constructor() : ViewModel() {

    val keyword = MutableStateFlow<String?>(null)
    private val _wifiList = MutableLiveData<List<WifiItem>>(emptyList())
    val wifiList: LiveData<List<WifiItem>> = _wifiList

    init {
        initAdapter()
    }

    private fun initAdapter() {
        val list = mutableListOf<WifiItem>().apply {
            add(WifiItem("WIFI112"))
            add(WifiItem("WIFI11223"))
            add(WifiItem("WIFI11223344"))
            add(WifiItem("WIFI1122334456"))
            add(WifiItem("WIFI11223344556"))
            add(WifiItem("WIFIassbb1122332"))
            add(WifiItem("WIFIassbb1122332"))
            add(WifiItem("WIFIassbb1122332"))
            add(WifiItem("WIFIassbb1122332"))
            add(WifiItem("WIFIassbb1122332"))
            add(WifiItem("WIFIassbb1122332"))
            add(WifiItem("WIFIassbb1122332"))
            add(WifiItem("WIFIassbb1122332"))
        }
        _wifiList.postValue(list)
    }

    fun input() {
        keyword.value = "12"
    }

    fun updateKeyword(text: String) {
        keyword.value = text
    }
}