package jp.co.vegeta.medium.view.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.vegeta.core.extentions.SingleLiveEvent
import jp.co.vegeta.test_repo.FlowGetJobRepository
import jp.co.vegeta.test_repo.FlowShowJobRepository
import jp.co.vegeta.test_repo.GuidanceStatusRepository
import jp.co.vegeta.usecase.UseCaseFlowJob
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/03/12.
 */
class NextScreenViewModel @ViewModelInject constructor(
    private val useCaseFlowJob: UseCaseFlowJob,
    private val guidanceStatusRepository: GuidanceStatusRepository
) : ViewModel() {

    private val _closeRequest = SingleLiveEvent<Unit>()
    val closeRequest: LiveData<Unit> = _closeRequest

    private val _openRequest = MutableLiveData<Unit>()
    val openRequest: LiveData<Unit> = _openRequest

    fun close() {
        _closeRequest.postValue(Unit)
    }

    fun open() {
        viewModelScope.launch {
            if (guidanceStatusRepository.guidance.firstOrNull() == true) {
                Log.d("Vegeta", "NOT OK ")
            } else {
                Log.d("vegeta", "OK")
            }
            Log.d("Vegeta", "Stub")
        }
        //_openRequest.postValue(Unit)
    }

    fun runJob() {
        viewModelScope.launch {
            useCaseFlowJob.execute()
        }
    }
}