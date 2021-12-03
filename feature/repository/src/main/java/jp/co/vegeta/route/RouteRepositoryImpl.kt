package jp.co.vegeta.route

import jp.co.vegeta.model.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2021/09/04.
 */
class RouteRepositoryImpl @Inject constructor() : RouteRepository {
    private val mutableRouteData = MutableStateFlow(Route.Unknown)
    override val data: Flow<Route>
        get() = mutableRouteData

    override suspend fun start() {
        var eta = 60L
        var distance = 2.0
        while (true) {
            eta += 3
            distance += 5
            mutableRouteData.value = makeRouteData(eta, distance)
            delay(1000L)
        }
    }

    override suspend fun composeRoute() {
        val eta = 60L
        val distance = 2.0
        mutableRouteData.value = makeRouteData(eta, distance)
    }

    private fun makeRouteData(estimate: Long, distance: Double): Route {
        return Route(
            id = 1,
            turnInfo = makeTurnInfo(estimate, distance)
        )
    }

    private fun makeTurnInfo(estimate: Long, distance: Double): List<Route.TurnInfo> {
        return mutableListOf<Route.TurnInfo>().apply {
            add(
                Route.TurnInfo(
                    name = "東京",
                    service = Route.Service.JCT,
                    estimateTime = estimate,
                    distance = distance
                )
            )
            add(
                Route.TurnInfo(
                    name = "調布",
                    service = Route.Service.IC,
                    estimateTime = (estimate * 1.5).toLong(),
                    distance = distance * 1.2
                )
            )
            add(
                Route.TurnInfo(
                    name = "八王子",
                    service = Route.Service.SA,
                    estimateTime = (estimate * 2.5).toLong(),
                    distance = distance * 1.8
                )
            )
        }
    }
}