package jp.co.vegeta.user

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/09/04.
 */
interface UserRepository {
    val data: Flow<Unit>
}