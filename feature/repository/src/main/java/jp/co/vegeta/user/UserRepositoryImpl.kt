package jp.co.vegeta.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2021/09/04.
 */
class UserRepositoryImpl @Inject constructor() : UserRepository {
    private val mutableData = MutableStateFlow(Unit)
    override val data: Flow<Unit>
        get() = mutableData
}