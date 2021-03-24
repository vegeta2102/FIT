package jp.co.vegeta.model

/**
 * Created by vegeta on 2021/03/24.
 */
data class CountDown(
    val time: Int,
    val progress: Int,
    val isProgressFinish: Boolean
)