package jp.co.vegeta.test_repo

import android.util.Log
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by vegeta on 2021/06/30.
 */
class FlowGetJobRepositoryImpl @Inject constructor() : FlowGetJobRepository {
    override suspend fun get() {
        for (i in 1..100)
            Log.d("FlowJob", "get($i)")
        delay(10_000L)
    }
}