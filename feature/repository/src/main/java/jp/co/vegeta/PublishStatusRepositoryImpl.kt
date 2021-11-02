package jp.co.vegeta

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by vegeta on 2021/10/06.
 */
class PublishStatusRepositoryImpl @Inject constructor() : PublishStatusRepository {
    private val _mutableData = MutableStateFlow<Boolean>(false)
    override val data: Flow<Boolean>
        get() = _mutableData

    private var randomNumber = 0
    override suspend fun sendMessage(onSuccess: (String) -> Unit, onFailure: (Throwable) -> Unit) {
        randomNumber = Random.nextInt()
        Timber.d("State sendMessage start")
        Timber.d("State sendMessage delay 2s")
        delay(2000L)
        if (randomNumber % 2 == 0) {
            onSuccess.invoke("Good number")
        } else {
            onFailure.invoke(Throwable("Odd number"))
        }
    }

    override fun test(): String {
        randomNumber = Random.nextInt()
        if (randomNumber % 2 == 0) {
            return randomNumber.toString()
        } else {
            throw Exception("Hello")
        }
    }
}
