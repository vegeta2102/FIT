package jp.co.vegeta.fit.suggest_search

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.vegeta.user.RegularCheckActiveRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by vegeta on 2022/05/18.
 */
@HiltViewModel
class SuggestSearchViewModel @Inject constructor(
    private val regularCheckActiveRepository: RegularCheckActiveRepository
) : ViewModel(), LifecycleObserver {

    private val _userList = MutableLiveData<List<String>>()
    val userList: LiveData<List<String>> = _userList

    private val _searchQueryText = MutableLiveData("")
    val searchQueryText: LiveData<String> =
        _searchQueryText.map { it.trimStart() }.distinctUntilChanged()

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun init() {
        viewModelScope.launch {
            regularCheckActiveRepository.syncMapMarker().onEach {
                Timber.d("Vegeta $it")
            }.launchIn(this)
        }
        // startCheck()
        viewModelScope.launch {
            regularCheckActiveRepository.check()
        }
        /*viewModelScope.launch {
            try {
                withTimeout(5000L) {
                    delay(6000L)
                    viewModelScope.launch {
                        regularCheckActiveRepository.data.collect {
                            Timber.d("Vegeta value $it")
                        }
                    }
                }
            } catch (e: TimeoutCancellationException) {
                Timber.d("Vegeta timeout")
            }

        }*/
        _userList.postValue(
            listOf(
                "Vu123",
                "Vu1",
                "VuVeveveve",
                "Minh12",
                "HoangHoangHoanghuoang",
                "LeAB",
                "ThiAA",
                "LanB",
                "Huongasdf",
                "Hoiasd",
                "Beoasdf"
            ).distinct()
        )
    }

    private fun startCheck() {
        viewModelScope.launch {
            Timber.d("Vegeta start check")
            regularCheckActiveRepository.data.distinctUntilChanged().collect {
                Timber.d("Vegeta $it")
            }
            /*try {
                withTimeout(2000L) {
                    regularCheckActiveRepository.data.collect {
                        Timber.d("Vegeta $it")
                    }
                }
            } catch (e: TimeoutCancellationException) {
                Timber.d("Vegeta timeout")
            }*/
        }
        viewModelScope.launch {
            regularCheckActiveRepository.data.onEach {
                Timber.d("Testing ${it * 2}")
            }.launchIn(this)
        }
    }

    fun selectPadKey() {
        val key = listOf("H", "1", "回", "ロ", "山").random()
        _searchQueryText.postValue(
            _searchQueryText.value?.plus(key)
        )
    }

    fun selectMoreText() {
        _searchQueryText.postValue(
            _searchQueryText.value?.plus("あいAうえお")
        )
    }

    fun delete() {
        val currentText = _searchQueryText.value.takeIf { it.isNullOrEmpty().not() } ?: return
        _searchQueryText.postValue(
            currentText.dropLast(1)
        )
    }
}