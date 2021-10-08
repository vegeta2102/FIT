package jp.co.vegeta

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/10/06.
 */
interface PublishStatusRepository {
    val data: Flow<Boolean>
    suspend fun sendMessage(onSuccess: (String) -> Unit, onFailure: (Throwable) -> Unit)
}
