package jp.co.vegeta.test_repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2021/07/08.
 */
class GuidanceStatusRepositoryImpl @Inject constructor() : GuidanceStatusRepository {
    private val mutableGuidance = MutableSharedFlow<Boolean>(replay = 0)
    override val guidance: Flow<Boolean>
        get() = mutableGuidance
}