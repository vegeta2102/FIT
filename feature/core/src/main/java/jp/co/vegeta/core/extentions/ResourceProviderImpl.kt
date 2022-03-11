package jp.co.vegeta.core.extentions

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat

/**
 * Created by vegeta on 2020/11/24.
 */
class ResourceProviderImpl(private val context: Context) :
    ResourceProvider {

    override fun getString(resId: Int): String =
        context.getString(resId)

    override fun getString(resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

    override fun getColor(color: Int): Int = ContextCompat.getColor(context, color)

    override fun getDimensionPixelSize(@DimenRes resId: Int): Int =
        context.resources.getDimensionPixelSize(resId)

    override fun getInteger(@IntegerRes resId: Int): Int =
        context.resources.getInteger(resId)

    override fun getRawFileDescriptor(resId: Int): AssetFileDescriptor =
        context.resources.openRawResourceFd(resId)

    override fun getColorStateList(color: Int): ColorStateList =
        context.getColorStateList(color)
}