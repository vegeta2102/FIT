package jp.co.vegeta.fit

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import app.mobilitytechnologies.lib.map.globalnavi.view.MapPresenter
import app.mobilitytechnologies.lib.map.globalnavi.view.MapView
import app.mobilitytechnologies.lib.map.model.vo.MapScale
import app.mobilitytechnologies.lib.map.view.MapRender
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * 地図テーマやデイ・ナイトモード反映をするMapView
 */
@AndroidEntryPoint
class CustomMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val view = MapView(context, attrs, defStyle).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CustomMapView).apply {
            view.setZOrderMediaOverlay(this.getBoolean(R.styleable.CustomMapView_isOverlay, false))
            recycle()
        }
        launch {
            delay(5L)
            addView(view)
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        view.visibility = visibility
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.cancel()
    }

    suspend fun initMap(
        scale: MapScale? = null,
        uriString: String = "",
        enableTouchEvent: Boolean = true,
        fontSizeText: MapRender.FontSize? = MapRender.FontSize.LARGE
    ): MapPresenter {
        Timber.tag("MapView@${this.hashCode().toString(16)}").d("initMap")
        return withContext(Dispatchers.IO) {
            val path = if (uriString.isEmpty()) {
                "file://${context.filesDir}/mapCustomize/mapCustomize/mapCustomize.json"
            } else {
                uriString
            }
            val fontSize = fontSizeText ?: MapRender.FontSize.LARGE
            val mapPresenter =
                view.initMap(
                    scale = scale,
                    uriString = path,
                    enableTouchEvent = enableTouchEvent,
                    fontSize = fontSize
                ) { tag, message ->
                    Timber.tag(tag).d(message)
                }
            // 全て地図画面で交通情報アイコンを表示にする
            mapPresenter.setVisibilityTrafficIcon(true)
            mapPresenter
        }
    }
}