package jp.co.vegeta.model

/**
 * Created by vegeta on 2021/02/15.
 */
sealed class SnackBar(open val text: String = "", open val duration: Long = 0) {
    object Hide: SnackBar()
    sealed class Show : SnackBar() {
        data class PrefixFare(override val text: String, override val duration: Long) : Show()
        data class DesiredTime(override val text: String, override val duration: Long) : Show()
        data class WheelChairSlop(override val text: String, override val duration: Long) : Show()
        data class WheelChairTransfer(override val text: String, override val duration: Long) : Show()
        data class SlideDoor(override val text: String, override val duration: Long) : Show()
        data class PriorityPass(override val text: String, override val duration: Long) : Show()
        data class AddMarkInfo(override val text: String, override val duration: Long) : Show()
        data class ChangeMarkInfo(override val text: String, override val duration: Long) : Show()
        data class SetDropoffPoint(override val text: String, override val duration: Long) : Show()
        data class ChangeDropoffPoint(override val text: String, override val duration: Long) : Show()
    }
}