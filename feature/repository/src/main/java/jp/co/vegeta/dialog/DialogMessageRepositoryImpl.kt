package jp.co.vegeta.dialog

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2021/06/30.
 */
class DialogMessageRepositoryImpl @Inject constructor() : DialogMessageRepository {
    private val _dialogMessageFlow = MutableSharedFlow<String?>(replay = 1)
    override val dialogMessageFlow: Flow<String?>
        get() = _dialogMessageFlow

    override suspend fun emit(content: String) {
        _dialogMessageFlow.emit(content)
    }
}