package jp.co.vegeta.fit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import jp.co.vegeta.mapbox.map.MapModuleManager
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by vegeta on 2021/01/23.
 */
@HiltAndroidApp
class FitApp : Application() {

    @Inject
    lateinit var mapModuleManager: MapModuleManager

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        runBlocking {
            initMap()
        }
        Timber.plant(Timber.DebugTree())
    }

    private suspend fun initMap() {
        withContext(currentCoroutineContext()) {
            mapModuleManager.initMap()
        }
    }
}
