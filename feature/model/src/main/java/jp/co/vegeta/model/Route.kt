package jp.co.vegeta.model

/**
 * Created by vegeta on 2021/09/04.
 */
data class Route(
    val id: Int,
    val turnInfo: List<TurnInfo>
) {
    companion object {
        val Unknown = Route(
            id = Int.MIN_VALUE,
            turnInfo = listOf()
        )
    }

    data class TurnInfo(
        val name: String,
        val service: Service,
        val estimateTime: Long,
        val distance: Double
    )

    enum class Service {
        UNKNOWN,
        SA,
        PA,
        JCT,
        IC
    }
}
