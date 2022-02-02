package jp.co.vegeta.core.extentions

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

val Float.dp get() =
    if (
        (Resources.getSystem().displayMetrics.densityDpi in DisplayMetrics.DENSITY_XHIGH + 1 until DisplayMetrics.DENSITY_XXHIGH) &&
        (Resources.getSystem().displayMetrics.density == 2.25f) // 本質的にはDPIよりもdensityが2になっていないのが原因かもしれない
    ) {
        // HUAWEI M3 lite 8″ / M5 lite 8″ の端末は360dpiとなりデザイン崩れが起きるため320dpi相当に決め打ちで変換
        this * 2.0f
    } else {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        )
    }

val Int.dp get() = toFloat().dp.toInt()