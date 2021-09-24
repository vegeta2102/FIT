package jp.co.vegeta.mapbox

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import jp.co.vegeta.mapbox.map.MapPresenter

/**
 * Created by vegeta on 2021/09/17.
 */
class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : SurfaceView(context, attrs, defStyle) {

    private var mapPresenterHolder: MapPresenter? = null
    val mapPresenter: MapPresenter
        get() = checkNotNull(mapPresenterHolder)
}
