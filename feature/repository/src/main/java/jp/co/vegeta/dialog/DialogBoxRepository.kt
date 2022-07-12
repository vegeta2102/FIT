package jp.co.vegeta.dialog

import jp.co.vegeta.model.DialogBoxContent
import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2022/07/12.
 */
interface DialogBoxRepository {
    val requestShowDialog: Flow<DialogBoxContent?>
    val requestHideDialog: Flow<Unit?>
    suspend fun show(message: String)
    suspend fun hide()
    suspend fun showWithTimeout(
        message: String,
        action: suspend () -> Unit,
        doNextAction: (suspend () -> Unit)? = null
    )
}