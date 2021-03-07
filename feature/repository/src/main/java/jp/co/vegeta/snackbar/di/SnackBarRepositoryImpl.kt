package jp.co.vegeta.snackbar.di

import jp.co.vegeta.model.SnackBar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by vegeta on 2021/02/15.
 */
internal class SnackBarRepositoryImpl : SnackBarRepository {
    private val supervisorJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + supervisorJob)
    private var delayJob: Job? = null
    private val _data = MutableStateFlow<SnackBar?>(null)
    override val data: Flow<SnackBar?>
        get() = _data

    override suspend fun push(value: SnackBar) {
        _data.value = value
        delay(value.duration)
        _data.value = SnackBar.Hide
    }
}