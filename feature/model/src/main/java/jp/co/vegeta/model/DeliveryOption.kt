package jp.co.vegeta.model

/**
 * Created by vegeta on 2021/08/15.
 */
sealed class DeliveryOption {
    abstract val id: Int
    abstract val name: String

    // To retain a toString() implementation from the base you can declare the method as final
    final override fun toString(): String = name

    data class Guy(
        override val id: Int,
        override val name: String
    ) : DeliveryOption()

    data class Dispatcher(
        override val id: Int,
        override val name: String
    ) : DeliveryOption()
}