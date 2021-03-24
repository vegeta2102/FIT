package jp.co.vegeta.progressbar.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import jp.co.vegeta.model.ProgressBarSetting
import jp.co.vegeta.progress.ProgressBarRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/03/24.
 */
class ProgressBarViewModel @ViewModelInject constructor(
    private val progressBarRepository: ProgressBarRepository
) : ViewModel() {

    val countDown: LiveData<String> = progressBarRepository.data.map {
        it.time.toString()
    }.asLiveData().distinctUntilChanged()

    val inCreaseNumber: LiveData<String> = countDown.map {
        10.minus(it.toInt()).toString()
    }

    val progress: LiveData<Int> = progressBarRepository.data.map {
        it.progress
    }.asLiveData().distinctUntilChanged()

    private val _buttonEnable = MutableLiveData<Boolean>(true)
    val buttonEnable: LiveData<Boolean>
        get() = _buttonEnable.distinctUntilChanged()

    val maxProgress = ProgressBarSetting.SECONDS_TIME.raw.times(100)

    fun start() {
        viewModelScope.launch {
            // Count down 10 seconds
            progressBarRepository.startCountDown(10)
        }
    }

    init {
        viewModelScope.launch {
            progressBarRepository.data.map {
                it.isProgressFinish
            }.collect {
                _buttonEnable.value = it
            }
        }
    }
}