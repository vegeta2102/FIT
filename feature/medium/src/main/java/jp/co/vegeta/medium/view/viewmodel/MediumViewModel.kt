package jp.co.vegeta.medium.view.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import jp.co.vegeta.core.extentions.SingleLiveEvent
import jp.co.vegeta.core.extentions.withNoNulls
import jp.co.vegeta.dialog.DialogMessageRepository
import jp.co.vegeta.medium.MediumRepository
import jp.co.vegeta.model.DeliveryOption
import jp.co.vegeta.usecase.UseCaseCheckGuidance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/03/08.
 */
class MediumViewModel @ViewModelInject constructor(
    private val mediumRepository: MediumRepository,
    private val dialogMessageRepository: DialogMessageRepository,
    private val useCaseCheckGuidance: UseCaseCheckGuidance
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(3000L)
            mediumRepository.fetch()
        }
    }

    val guidance: LiveData<String> by lazy {
        Log.d("Medium", "Run?")
        useCaseCheckGuidance.execute().asLiveData()
    }

    val flowValue: LiveData<String>
        get() = mediumRepository.data.filterNotNull().asLiveData()

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int>
        get() = _progress

    fun clickUp() {
        var test1: String? = null
        var test2: String? = null
        test1 = "1212"
        test2 = test1.takeIf {
            it.length > 10
        }?.plus("hello")
        withNoNulls(test1, test2) { v1, v2 ->
            Log.d("Test", "Value: $v1, $v2")
        }

        val guy = DeliveryOption.Guy(
            id = 12,
            name = "People"
        )
        guy.toString()

        /*_progress.value?.let {
            _progress.value = (it + 1).coerceAtMost(10)
        }*/
        viewModelScope.launch {
            runCatching {
                mediumRepository.execute()
            }.onSuccess {
                mediumRepository.nextStep()
                Log.d("Vegeta", "onSuccess")
            }.onFailure {
                Log.d("Vegeta", "onFailure")
            }
        }
    }

    fun clickDown() {
        _progress.value?.let {
            _progress.value = (it - 1).coerceAtLeast(1)
        }
        viewModelScope.launch {
            dialogMessageRepository.emit("Testing")
        }
    }

    private val _nextRequest = SingleLiveEvent<Unit>()
    val nextRequest: LiveData<Unit>
        get() = _nextRequest

    fun showNextScreen() {
        _nextRequest.postValue(Unit)
    }
}