package jp.co.vegeta.medium.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import jp.co.vegeta.medium.MediumRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/03/08.
 */
class MediumViewModel @ViewModelInject constructor(
    private val mediumRepository: MediumRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            mediumRepository.fetch()
        }
    }

    val flowValue: LiveData<String>
        get() = mediumRepository.data.filterNotNull().asLiveData()

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int>
        get() = _progress

    fun clickUp() {
        _progress.value?.let {
            _progress.value = (it + 1).coerceAtMost(10)
        }
    }

    fun clickDown() {
        _progress.value?.let {
            _progress.value = (it - 1).coerceAtLeast(1)
        }
    }

    private val _nextRequest = MutableLiveData<Unit>()
    val nextRequest: LiveData<Unit>
        get() = _nextRequest

    fun showNextScreen() {
        _nextRequest.postValue(Unit)
    }
}