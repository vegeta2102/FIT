package jp.co.vegeta.fit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.vegeta.PublishStatusRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by vegeta on 2021/02/15.
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val publishStatusRepository: PublishStatusRepository
) : ViewModel() {
    private val _address = MutableLiveData("東京")
    val address: LiveData<String> = _address

    private val _addressSuggestionList = MutableLiveData<List<Address>>(emptyList())
    val addressSuggestionList: LiveData<List<Address>> = _addressSuggestionList
    private val _tokenError: MutableSharedFlow<Event> = MutableSharedFlow(replay = 0)
    val tokenError: Flow<Event>
        get() = _tokenError

    private val _test: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val test: Flow<Unit?>
        get() = _test

    fun initObserve() {
        viewModelScope.launch {
            publishStatusRepository.tokenError.collect {
                Timber.d("Test: publish Unit")
            }
        }
        viewModelScope.launch {
            test.collect {
                Timber.d("Unit $it")
            }
        }
        viewModelScope.launch {
            tokenError.collect {
                Timber.d("Teset $it")
            }
        }
    }

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

    fun buttonWifiSetting() {
        viewModelScope.launch {
            publishStatusRepository.test()
        }
        var job: Job? = null
        job = viewModelScope.launch {
            kotlin.runCatching {
                //_tokenError.emit(Event.Test)
                throwError()
            }
            showlog()
            _tokenError.emit(Event.Test)
        }
    }

    private suspend fun throwError() {
        Timber.d("Hoi : Before throw error")
        throw IllegalAccessException()
    }

    private suspend fun showlog() {
        Timber.d("Hoi : Showlog")
    }

    sealed class Event {
        object Test : Event()
        object Product: Event()
    }
}
