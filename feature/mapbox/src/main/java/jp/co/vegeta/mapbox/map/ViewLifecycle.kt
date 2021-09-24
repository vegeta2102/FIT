package jp.co.vegeta.mapbox.map

import android.os.Bundle

/**
 * Created by vegeta on 2021/09/23.
 */
interface ViewLifecycle {
    fun onCreate(savedInstance: Bundle?)
    fun onResume()
    fun onStart()
    fun onStop()
    fun onPause()
    fun onDestroy()
    fun onLowMemory()
    fun onSaveInstanceState(outState: Bundle)
}
