package jp.co.vegeta.test_repo

import android.util.Log
import javax.inject.Inject

/**
 * Created by vegeta on 2021/06/30.
 */
class FlowShowJobRepositoryImpl @Inject constructor() : FlowShowJobRepository {
    override suspend fun show() {
        for (i in 1..100)
            Log.d("FlowJob", "show($i)")
    }
}