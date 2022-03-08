package jp.co.vegeta

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Created by vegeta on 2021/10/06.
 */
interface PublishStatusRepository {
    val data: Flow<Boolean>
    suspend fun sendMessage(onSuccess: (String) -> Unit, onFailure: (Throwable) -> Unit)
    suspend fun test(): String
    val tokenError: Flow<Unit>
}
