package jp.co.vegeta.user

import android.os.SystemClock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by vegeta on 2022/06/13.
 */
class RegularCheckActiveRepositoryImpl @Inject constructor() : RegularCheckActiveRepository {
    private val _data = MutableStateFlow<Int>(0)
    override val data: Flow<Int>
        get() = _data

    override suspend fun check() {
        while (true) {
            delay(2000L)
            _data.emit(Random.nextInt())
        }
    }

    override suspend fun syncMapMarker(): Flow<String> {
        return flowOf("1", "2", "3", "4").map {
            delay(5000)
            it
        }
        /*return flow<String> {
            delay(1000)
            emit("Test1")
            delay(1000)
            emit("Test2")
            delay(2000)
            emit("Test3")
            delay(3000)
            emit("Test4")
        }*/
    }

    override suspend fun syncWithCallback(
        onSuccess: (String) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ) {
        delay(3000)
        onSuccess.invoke("Testing")
        delay(3000)
        onFailure.invoke(Throwable("Error testing"))
    }
}