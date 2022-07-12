package jp.co.vegeta.dialog

import jp.co.vegeta.model.DialogBoxContent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by vegeta on 2022/07/12.
 */
class DialogBoxRepositoryImpl @Inject constructor() : DialogBoxRepository {
    private val _requestShowDialog = MutableSharedFlow<DialogBoxContent?>(replay = 0)
    override val requestShowDialog: Flow<DialogBoxContent?>
        get() = _requestShowDialog

    private val _requestHideDialog = MutableSharedFlow<Unit?>(replay = 0)
    override val requestHideDialog: Flow<Unit?>
        get() = _requestHideDialog

    override suspend fun show(message: String) {
        _requestShowDialog.emit(DialogBoxContent(message = message))
    }

    override suspend fun hide() {
        _requestHideDialog.emit(Unit)
    }

    override suspend fun showWithTimeout(
        message: String,
        action: suspend () -> Unit,
        doNextAction: (suspend () -> Unit)?
    ) {
        coroutineScope {
            val job = launch {
                show(message)
            }
            action()

            job.cancel()
            hide()

            doNextAction?.invoke()
        }
    }
}