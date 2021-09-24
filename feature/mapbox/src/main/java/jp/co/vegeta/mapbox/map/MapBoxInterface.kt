package jp.co.vegeta.mapbox.map

import com.mapbox.mapboxsdk.maps.OnMapReadyCallback

/**
 * Created by vegeta on 2021/09/23.
 */
interface MapBoxInterface : ViewLifecycle {
    fun syncMap(
        styleUri: String,
        callback: OnMapReadyCallback? = null,
        onLoadFinished: (() -> Unit)? = null
    )
}
