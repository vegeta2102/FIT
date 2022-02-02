package jp.co.vegeta.fit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

/**
 * Created by vegeta on 2021/02/15.
 */
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor() : ViewModel() {
    private val _address = MutableLiveData("東京")
    val address: LiveData<String> = _address

    private val _addressSuggestionList = MutableLiveData<List<Address>>(emptyList())
    val addressSuggestionList: LiveData<List<Address>> = _addressSuggestionList

    fun buttonClick(index: Int) {
        Timber.d("Click $index")
        _address.postValue("$index")
        when (index) {
            1 -> {
                _addressSuggestionList.postValue(
                    mutableListOf<Address>().apply {
                        add(Address("Tokyo"))
                        add(Address("TokyoStation"))
                        add(Address("TokyoTowerAasfa"))
                        add(Address("TokyoTowerb"))
                        add(Address("TokyoTowersdf"))
                        add(Address("TokyoTowerdaf"))
                        add(Address("TokyoTowerasdf"))
                        add(Address("TokyoTowerdsaf"))
                        add(Address("TokyoTowerasdf"))

                    }
                )
            }
            2 -> {
                _addressSuggestionList.postValue(
                    mutableListOf<Address>().apply {
                        add(Address("ChofuBalll"))
                        add(Address("ChofuStatium"))
                        add(Address("ChofuFudaHospital"))
                    }
                )
            }
        }
    }
}
