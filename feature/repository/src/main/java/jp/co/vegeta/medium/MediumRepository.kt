package jp.co.vegeta.medium

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/03/08.
 */
interface MediumRepository {
    suspend fun fetch()
    val data: Flow<String?>

    suspend fun execute()
    suspend fun nextStep()
}