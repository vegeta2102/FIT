package jp.co.vegeta.core.extentions

import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import androidx.annotation.*

interface ResourceProvider {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String

    fun getColor(@ColorRes color: Int): Int

    fun getDimensionPixelSize(@DimenRes resId: Int): Int

    fun getInteger(@IntegerRes resId: Int): Int

    fun getRawFileDescriptor(@RawRes resId: Int): AssetFileDescriptor

    fun getColorStateList(@ColorRes color: Int): ColorStateList

    fun loadJson(fileName: String): String?
}
