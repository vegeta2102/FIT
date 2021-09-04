package jp.co.vegeta.medium.view.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import jp.co.vegeta.core.extentions.SingleLiveEvent
import jp.co.vegeta.core.extentions.withNoNulls
import jp.co.vegeta.dialog.DialogMessageRepository
import jp.co.vegeta.medium.MediumRepository
import jp.co.vegeta.model.DeliveryOption
import jp.co.vegeta.model.State
import jp.co.vegeta.usecase.UseCaseCheckGuidance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/03/08.
 */
class MediumViewModel @ViewModelInject constructor(
    private val mediumRepository: MediumRepository,
    private val dialogMessageRepository: DialogMessageRepository,
    private val useCaseCheckGuidance: UseCaseCheckGuidance
) : ViewModel(), LifecycleObserver {

    init {
        viewModelScope.launch {
            delay(3000L)
            mediumRepository.fetch()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onStart() {
        Log.d("Vegeta", "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d("Vegeta", "Call again?")
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
    val testEnum = mediumRepository.testEnumData.asLiveData()
    init {
        viewModelScope.launch {
            mediumRepository.testData.collect {
                Log.d("TestData", "$it")
            }
        }
    }


    fun clickDown() {
        /*_progress.value?.let {
            _progress.value = (it - 1).coerceAtLeast(1)
        }
        viewModelScope.launch {
            dialogMessageRepository.emit("Testing")
        }*/
        viewModelScope.launch {
            val list = mediumRepository.testData.firstOrNull()
            Log.d("TestDataList", "$list")

        }
    }

    private val _nextRequest = SingleLiveEvent<Unit>()
    val nextRequest: LiveData<Unit>
        get() = _nextRequest

    private var i = 1
    fun showNextScreen() {
        /*val mutableList = mutableListOf<DeliveryOption>().apply {
            add(DeliveryOption.Dispatcher(id = 12, name = "12"))
            // add(DeliveryOption.Guy(id = 13, name = "13"))
        }*/
        // Log.d("Vegeta", mutableList.toSuffix())
        // _nextRequest.postValue(Unit)
        i += 1
        viewModelScope.launch {
            mediumRepository.testEmit(i)
            mediumRepository.testEmitEnum(State.OK)
        }
    }

    private fun List<DeliveryOption>.toSuffix(): String {
        return this.joinToString(separator = "・") {
            when (it) {
                is DeliveryOption.Dispatcher -> "Dispatcher"
                is DeliveryOption.Guy -> "Guy"
            }
        }
    }
}