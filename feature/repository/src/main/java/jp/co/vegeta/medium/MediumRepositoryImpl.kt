package jp.co.vegeta.medium

import android.util.Log
import jp.co.vegeta.model.State
import jp.co.vegeta.model.StateData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by vegeta on 2021/03/08.
 */
class MediumRepositoryImpl : MediumRepository {
    init {
        Log.d("hoangvm", "When call?")
    }

    private val mutableData = MutableStateFlow<String?>(null)
    private val mutableTestData = MutableStateFlow<StateData>(StateData(0))
    private val mutableTestEnumData = MutableStateFlow<State>(State.NG)
    override suspend fun fetch() {
        mutableData.emit("Hello")
    }

    override val data: Flow<String?>
        get() = mutableData
    override val testData: Flow<StateData>
        get() = mutableTestData
    override val testEnumData: Flow<State>
        get() = mutableTestEnumData

    override suspend fun execute() {
        Log.d("Vegeta", "execute")
        delay(5000L)
    }

    override suspend fun nextStep() {
        Log.d("Vegeta", "nextStep")
        throw IllegalAccessException("LoL")
    }

    override suspend fun testEmit(value: Int) {
        mutableTestData.value = StateData(value)
    }

    override suspend fun testEmitEnum(value: State) {
        mutableTestEnumData.value = value
    }
}