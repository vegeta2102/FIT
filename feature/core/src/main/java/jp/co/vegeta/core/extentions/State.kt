package jp.co.vegeta.core.extentions

/**
 * Created by vegeta on 2021/10/08.
 */
sealed class State<out T> {
    class Success<T>(val data: T) : State<T>() {
        override fun toString(): String = "Success:$data"
    }

    class Failure(val throwable: Throwable) : State<Nothing>() {
        override fun toString(): String = "Failure:$throwable"
    }
}
