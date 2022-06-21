package jp.co.vegeta.user

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
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
}