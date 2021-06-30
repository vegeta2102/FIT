package jp.co.vegeta.medium

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by vegeta on 2021/03/08.
 */
class MediumRepositoryImpl : MediumRepository {
    private val mutableData = MutableStateFlow<String?>(null)
    override suspend fun fetch() {
        mutableData.emit("Hello")
    }

    override val data: Flow<String?>
        get() = mutableData

    override suspend fun execute() {
        Log.d("Vegeta", "execute")
        delay(5000L)
    }

    override suspend fun nextStep() {
        Log.d("Vegeta", "nextStep")
        throw IllegalAccessException("LoL")
    }
}