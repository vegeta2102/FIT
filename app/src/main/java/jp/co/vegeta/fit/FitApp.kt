package jp.co.vegeta.fit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by vegeta on 2021/01/23.
 */
@HiltAndroidApp
class FitApp : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Timber.plant(Timber.DebugTree())
    }
}
