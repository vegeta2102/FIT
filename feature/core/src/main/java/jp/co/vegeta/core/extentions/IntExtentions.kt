package jp.co.vegeta.core.extentions

/**
 * Created by vegeta on 2022/03/15.
 */

fun Int?.toAmountString(): String {
    return this?.let {
        String.format("%,d", it)
    } ?: "0"
}