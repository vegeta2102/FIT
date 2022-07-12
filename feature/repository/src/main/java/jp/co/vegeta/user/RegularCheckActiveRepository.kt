package jp.co.vegeta.user

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2022/06/13.
 */
interface RegularCheckActiveRepository {
    val data: Flow<Int>
    suspend fun check()
    suspend fun syncMapMarker(): Flow<String>
    suspend fun syncWithCallback(onSuccess: (String) -> Unit, onFailure: (error: Throwable) -> Unit)
}