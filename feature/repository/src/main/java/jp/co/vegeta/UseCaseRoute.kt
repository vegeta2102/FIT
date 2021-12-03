package jp.co.vegeta

import jp.co.vegeta.route.RouteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by vegeta on 2021/12/03.
 */
class UseCaseRoute(private val routeRepository: RouteRepository) {
    suspend fun execute(scope: CoroutineScope) {
        scope.launch {
            routeRepository.data.collect {
                Timber.d("RouteId $it")
            }
        }
        scope.launch {
            routeRepository.data.collect {
                Timber.d("RouteIDhehe")
            }
        }
    }
}
