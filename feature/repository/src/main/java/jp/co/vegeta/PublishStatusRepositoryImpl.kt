package jp.co.vegeta

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2021/10/06.
 */
class PublishStatusRepositoryImpl @Inject constructor() : PublishStatusRepository {
    private val _mutableData = MutableStateFlow<Boolean>(false)
    override val data: Flow<Boolean>
        get() = _mutableData
}
