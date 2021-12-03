package jp.co.vegeta.route

import jp.co.vegeta.model.Route
import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/09/04.
 */
interface RouteRepository {
    val data: Flow<Route>
    suspend fun start()
    suspend fun composeRoute()
}