package jp.co.vegeta.fit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.vegeta.PublishStatusRepository
import jp.co.vegeta.UseCaseRoute
import jp.co.vegeta.core.extentions.State
import jp.co.vegeta.route.RouteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

/**
 * Created by vegeta on 2021/02/15.
 */
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val publishStatusRepository: PublishStatusRepository,
    private val useCaseRoute: UseCaseRoute,
    private val routeRepository: RouteRepository,
) : ViewModel() {

    fun init() {
        observeRoute()
    }

    private fun observeRoute() {
        viewModelScope.launch {
            useCaseRoute.execute(this)
        }
    }

    fun onClick() {
        viewModelScope.launch {
            /*test()
            observeSetup().collect {
                Timber.d("State : $it")
            }*/
            routeRepository.composeRoute()
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

    private fun test(): String? {
        return try {
            Timber.d("Try")
            publishStatusRepository.test()
        } catch (e: Exception) {
            Timber.d("Catch")
            null
        } finally {
            Timber.d("Finally")
        }
    }
}
