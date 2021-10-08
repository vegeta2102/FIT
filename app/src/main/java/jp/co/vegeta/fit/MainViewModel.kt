package jp.co.vegeta.fit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.vegeta.PublishStatusRepository
import jp.co.vegeta.core.extentions.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by vegeta on 2021/02/15.
 */
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val publishStatusRepository: PublishStatusRepository
) : ViewModel() {

    fun onClick() {
        viewModelScope.launch {
            observeSetup().collect {
                Timber.d("State : $it")
            }
        }
    }

    private fun observeSetup(): Flow<State<String>> = callbackFlow {
        publishStatusRepository.sendMessage(
            { result ->
                offer(State.Success(result))
            },
            { throwable ->
                offer(State.Failure(throwable))
            }
        )
        awaitClose {
            cancel()
        }
    }
}
