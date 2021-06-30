package jp.co.vegeta.dialog

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/06/30.
 */
interface DialogMessageRepository {
    val dialogMessageFlow: Flow<String?>
    suspend fun emit(
        content: String
    )
}