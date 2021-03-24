package jp.co.vegeta.progress

import jp.co.vegeta.model.CountDown
import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/03/24.
 */
interface ProgressBarRepository {
    val data: Flow<CountDown>
    suspend fun startCountDown(count: Int)
}