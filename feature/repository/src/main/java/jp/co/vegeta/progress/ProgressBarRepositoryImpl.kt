package jp.co.vegeta.progress

import jp.co.vegeta.model.CountDown
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

/**
 * Created by vegeta on 2021/03/24.
 */
class ProgressBarRepositoryImpl @Inject constructor() : ProgressBarRepository {
    private val _data: MutableStateFlow<CountDown?> = MutableStateFlow(null)
    override val data: Flow<CountDown>
        get() = _data.filterNotNull()

    override suspend fun startCountDown(count: Int) {
        // 10 seconds for count down
        // 100 is a finer number that makes progress run smoother than normal
        val countTime = count * 100
        repeat(countTime) {
            // In case of 10 seconds count down
            // The timer will display as 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0
            // So it must plus 1
            val displaySeconds = countTime.minus(it).div(100).plus(1)

            // Repeat N will run from N-1 to 0
            val finish = countTime.minus(it) == 1

            // Emit second display and progress state
            _data.emit(CountDown(time = displaySeconds, progress = it, isProgressFinish = false))
            if (finish) {
                _data.emit(CountDown(time = 0, progress = countTime, isProgressFinish = finish))
            }
            delay(10)
        }
    }
}