package jp.co.vegeta.medium

import jp.co.vegeta.model.State
import jp.co.vegeta.model.StateData
import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/03/08.
 */
interface MediumRepository {
    suspend fun fetch()
    val data: Flow<String?>

    val testData: Flow<StateData>
    val testEnumData: Flow<State>

    suspend fun execute()
    suspend fun nextStep()
    suspend fun testEmit(value: Int)
    suspend fun testEmitEnum(value: State)
}